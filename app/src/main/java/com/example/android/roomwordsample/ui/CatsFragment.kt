package com.example.android.roomwordsample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.roomwordsample.R
import com.example.android.roomwordsample.databinding.FragmentCatsBinding
import com.example.android.roomwordsample.network.apiCats.CatsApi
import com.example.android.roomwordsample.network.apiCats.CatsJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import timber.log.Timber

var TAG = "NetworkFragment"

class CatsFragment : Fragment() {

    private var _binding: FragmentCatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_cats,
            container,
            false
        )

        (context as AppCompatActivity).supportActionBar!!.title = getString(R.string.app_name)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getCurrentData(binding)

        binding.layoutGenerateNewFact.setOnClickListener {
            getCurrentData(binding)
        }

    }

    private fun getCurrentData(binding: FragmentCatsBinding) {

        binding.tvTextView.visibility = View.GONE
        binding.tvTimeStamp.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.IO) {

            try {
                val response = CatsApi.retrofitService.getCatFacts().awaitResponse()
                if (response.isSuccessful) {

                    val data: CatsJson = response.body()!!
                    Timber.d(TAG, data.text)

                    withContext(Dispatchers.Main) {
                        binding.tvTextView.visibility = View.VISIBLE
                        binding.tvTimeStamp.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE

                        binding.tvTextView.text = data.text
                        binding.tvTimeStamp.text = data.createdAt
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Something wrong!", Toast.LENGTH_SHORT).show()
                }

            }

        }
    }

}