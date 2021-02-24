package com.example.android.roomwordsample.ui

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.Slide
import com.example.android.roomwordsample.R
import com.example.android.roomwordsample.WordViewModel
import com.example.android.roomwordsample.WordViewModelFactory
import com.example.android.roomwordsample.application.WordsApplication
import com.example.android.roomwordsample.database.Word
import com.example.android.roomwordsample.databinding.FragmentWordBinding
import com.example.android.roomwordsample.util.UtilMethods.hideKeyboard
import com.example.android.roomwordsample.util.themeColor
import com.google.android.material.transition.MaterialContainerTransform

class WordFragment : Fragment() {

    private var _binding: FragmentWordBinding? = null
    private val binding get() = _binding!!

    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((activity?.application as WordsApplication).repository)
    }

    private lateinit var editWordView: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word, container, false)

        (context as AppCompatActivity).supportActionBar!!.title = getString(R.string.app_name)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editWordView = binding.editWord

        binding.buttonSave.setOnClickListener {

            if (TextUtils.isEmpty(editWordView.text)) {

                findNavController().navigate(R.id.action_newWordFragment_to_mainFragment, null)
                Toast.makeText(
                    context,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
                ).show()

                hideKeyboard(view)

            } else {

                val word = editWordView.text.toString()
                wordViewModel.insert(Word(word))

                findNavController().navigate(R.id.action_newWordFragment_to_mainFragment, null)

                hideKeyboard(view)
            }

        }

        enterTransition = MaterialContainerTransform().apply {
            startView = requireActivity().findViewById(R.id.fab)
            endView = requireActivity().findViewById(R.id.word_card_view)
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
            containerColor = requireContext().themeColor(R.attr.colorSurface)
            startContainerColor = requireContext().themeColor(R.attr.colorSecondary)
            endContainerColor = requireContext().themeColor(R.attr.colorSurface)
        }
        returnTransition = Slide().apply {
            duration = resources.getInteger(R.integer.motion_duration_medium).toLong()
            addTarget(R.id.word_card_view)
        }

    }

}