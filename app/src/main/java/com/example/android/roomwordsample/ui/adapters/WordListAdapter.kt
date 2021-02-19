package com.example.android.roomwordsample.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomwordsample.WordViewModel
import com.example.android.roomwordsample.database.Word
import com.example.android.roomwordsample.databinding.RecyclerviewItemBinding
import com.example.android.roomwordsample.ui.MainFragmentDirections


class WordListAdapter(private val wordViewModel: WordViewModel) :
    ListAdapter<Word, WordListAdapter.WordViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): WordViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerviewItemBinding.inflate(inflater, parent, false)
        return WordViewHolder(
            binding,
            wordViewModel
        )
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {

        holder.name.text = wordViewModel.allWords.value!![position].word
        holder.versionName = wordViewModel.allWords.value!![position].word
    }

    class WordViewHolder(
        binding: RecyclerviewItemBinding,
        wordViewModel: WordViewModel
    ) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener,
        View.OnLongClickListener {

        var name: TextView = binding.textView
        var versionName: String? = null

        init {

            binding.textView.setOnClickListener { v ->
                val searchWord: String = wordViewModel.allWords.value!![adapterPosition].word
                val action = MainFragmentDirections.actionMainFragmentToNewsFragment(searchWord)
                Toast.makeText(v.context, "News for $searchWord", Toast.LENGTH_SHORT).show()
                findNavController(v).navigate(action)
            }

            binding.textView.setOnLongClickListener { v ->
                Toast.makeText(v.context, "Deleted: $versionName", Toast.LENGTH_SHORT).show()
                wordViewModel.deleteItem(wordViewModel.allWords.value!![adapterPosition])
                true
            }
        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

        override fun onLongClick(v: View?): Boolean {
            TODO("Not yet implemented")
            return true
        }

    }

    class WordsComparator : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.word == newItem.word
        }
    }
}
