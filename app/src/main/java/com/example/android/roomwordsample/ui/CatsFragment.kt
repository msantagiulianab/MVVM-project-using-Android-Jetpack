package com.example.android.roomwordsample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NetworkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

var TAG = "NetworkFragment"

class CatsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentCatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NetworkFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CatsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}