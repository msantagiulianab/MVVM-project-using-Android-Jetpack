package com.example.android.roomwordsample.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomwordsample.database.Word
import com.example.android.roomwordsample.databinding.RecyclerviewItemBinding


class WordListAdapter(
    private val clickListener: (Word) -> Unit
//                      private val longClickListener: (Word) -> Boolean
) :
    ListAdapter<Word, WordListAdapter.WordViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): WordViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerviewItemBinding.inflate(inflater, parent, false)
        return WordViewHolder(
            binding,
            clickListener
//            longClickListener
        )
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class WordViewHolder(
        private val binding: RecyclerviewItemBinding,
        private val clickListener: (Word) -> Unit
//        private val longClickListener: (Word) -> Boolean
    ) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener
//        View.OnLongClickListener
    {

        init {
            binding.textView.setOnClickListener(this)
//            binding.textView.setOnLongClickListener(this)
        }

        fun bind(item: Word) {
            binding.textView.text = item.word
            binding.textView.setOnClickListener { clickListener(item) }
//            binding.textView.setOnLongClickListener { longClickListener(item)}
        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

//        override fun onLongClick(v: View?): Boolean {
//            TODO("Not yet implemented")
//            return true
//        }


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