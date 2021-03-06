package com.example.android.roomwordsample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.roomwordsample.R
import com.example.android.roomwordsample.databinding.FragmentAuthBinding
import java.util.concurrent.Executors

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private lateinit var biometricPrompt: BiometricPrompt

    private val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Authenticate yourself")
        .setNegativeButtonText("Cancel")
        .build()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val executor = Executors.newSingleThreadExecutor()
        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                        // user clicked negative/cancel button
                    } else {

                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    activity?.runOnUiThread {
                        findNavController().navigate(R.id.action_authFragment_to_mainFragment)
                    }
                }


                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    activity?.runOnUiThread {
                        Toast.makeText(
                            context,
                            "Authentication failed! Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false)

        (context as AppCompatActivity).supportActionBar!!.title = getString(R.string.app_name)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAuthenticate.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }
}