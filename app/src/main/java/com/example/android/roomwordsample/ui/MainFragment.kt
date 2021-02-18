package com.example.android.roomwordsample.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.roomwordsample.R
import com.example.android.roomwordsample.WordViewModel
import com.example.android.roomwordsample.WordViewModelFactory
import com.example.android.roomwordsample.application.WordsApplication
import com.example.android.roomwordsample.database.Word
import com.example.android.roomwordsample.databinding.FragmentMainBinding
import com.example.android.roomwordsample.ui.adapters.WordListAdapter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((activity?.application as WordsApplication).repository)
    }


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
    ): View {

        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = WordListAdapter { onItemClicked(it) }
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        wordViewModel.allWords.observe(viewLifecycleOwner, { words ->
            words?.let {
                adapter.submitList(it)
            }
        })

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_newWordFragment)
        }

        binding.fabDelete.setOnClickListener {
            showDialog()
        }

        binding.fabNetwork.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_marsOverviewFragment)
        }

//        binding.fabNetwork.setOnClickListener {
//            findNavController().navigate(R.id.action_mainFragment_to_newsFragment)
//        }

        binding.fabNetwork.setOnLongClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_catsFragment)
            true
        }


//        val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
//            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//                override fun onMove(
//                    recyclerView: RecyclerView,
//                    viewHolder: RecyclerView.ViewHolder,
//                    target: RecyclerView.ViewHolder
//                ): Boolean {
//                    return false
//                }
//
//                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                    val position: Int = viewHolder.adapterPosition
//
//                    when(direction) {
//                        ItemTouchHelper.RIGHT -> {
//                            wordViewModel.deleteItem(adapter.getItemId(viewHolder.adapterPosition))
//                        }
//                    }
//                }
//            }


    }

    private fun showDialog() {

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("The database will be cleared")
        builder.setMessage("Are you sure?")

        builder.setPositiveButton("OK") { dialog, _ ->
            wordViewModel.deleteAll()
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()

    }

    private fun onItemClicked(item: Word) {

        val searchWord: String = item.word.toString()
        Toast.makeText(context, "News for $searchWord", Toast.LENGTH_SHORT).show()
        val action = MainFragmentDirections.actionMainFragmentToNewsFragment(searchWord)
        findNavController().navigate(action)

//        wordViewModel.deleteItem(item)
    }

//    private fun onItemLongClicked(item: Word): Boolean {
//
//        val searchWord: String = item.word.toString()
//        Toast.makeText(context, "News for $searchWord", Toast.LENGTH_SHORT).show()
//        val action = MainFragmentDirections.actionMainFragmentToNewsFragment(searchWord)
//        findNavController().navigate(action)
//        return true
//    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
