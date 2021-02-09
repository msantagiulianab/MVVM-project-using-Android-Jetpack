package com.example.android.roomwordsample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomwordsample.databinding.RecyclerviewItemBinding


class WordListAdapter(private val clickListener: (Word) -> Unit) :
    ListAdapter<Word, WordListAdapter.WordViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): WordViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerviewItemBinding.inflate(inflater, parent, false)
        return WordViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class WordViewHolder(
        private val binding: RecyclerviewItemBinding,
        private val clickListener: (Word) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var item: Word

        init {
            binding.textView.setOnClickListener(this)
        }

        fun bind(item: Word) {
            binding.textView.text = item.word
            binding.textView.setOnClickListener { clickListener(item) }
        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
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