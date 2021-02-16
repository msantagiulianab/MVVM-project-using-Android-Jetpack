package com.example.android.roomwordsample.ui

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.android.roomwordsample.R
import com.example.android.roomwordsample.WordViewModel
import com.example.android.roomwordsample.WordViewModelFactory
import com.example.android.roomwordsample.application.WordsApplication
import com.example.android.roomwordsample.database.Word
import com.example.android.roomwordsample.databinding.FragmentWordBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WordFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentWordBinding? = null
    private val binding get() = _binding!!

    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((activity?.application as WordsApplication).repository)
    }

    private lateinit var editWordView: EditText

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

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word, container, false)

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
    }

    private fun hideKeyboard(view: View) {
        val inputManager: InputMethodManager = view.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewWordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}