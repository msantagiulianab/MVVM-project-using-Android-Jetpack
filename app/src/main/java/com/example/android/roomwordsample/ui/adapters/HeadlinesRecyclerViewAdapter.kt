package com.example.android.roomwordsample.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.android.roomwordsample.R
import com.example.android.roomwordsample.databinding.ItemNewsRecyclerviewBinding
import com.example.android.roomwordsample.network.apiNews.models.NewsHeadlines
import com.example.android.roomwordsample.ui.NewsFragmentDirections
import com.example.android.roomwordsample.util.UtilMethods.convertISOTime

class HeadlinesRecyclerViewAdapter(
    var context: Context,
    var newsheadlines: List<NewsHeadlines>
) :
    RecyclerView.Adapter<HeadlinesRecyclerViewAdapter.HeadlinesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlinesViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsRecyclerviewBinding.inflate(inflater, parent, false)
        return HeadlinesViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int = newsheadlines.size

    override fun onBindViewHolder(holder: HeadlinesViewHolder, position: Int) {

        if (newsheadlines[position].title != null) {
            holder.text.text = newsheadlines[position].title
        } else if (newsheadlines[position].description != null) {
            holder.text.text = newsheadlines[position].description
        } else if (newsheadlines[position].content != null) {
            holder.text.text = newsheadlines[position].content
        }

        Glide.with(context)
            .load(newsheadlines[position].urlToImage)
            .placeholder(R.drawable.index)
            .into(holder.image)

        holder.date.text = convertISOTime(context, newsheadlines[position].publishedAt)

        holder.item.setOnClickListener {

            val newsCardDetailTransitionName =
                context.resources.getString(R.string.news_card_detail_transition_name)
            val extras = FragmentNavigatorExtras(it to newsCardDetailTransitionName)
            val action = NewsFragmentDirections.actionNewsFragmentToSingleNewsFragment(
                newsheadlines[position].urlToImage!!,
                newsheadlines[position].content!!,
                newsheadlines[position].url!!
            )
            findNavController(holder.itemView).navigate(action, extras)
        }
    }

    class HeadlinesViewHolder(
        binding: ItemNewsRecyclerviewBinding
    ) : ViewHolder(binding.root),
        View.OnClickListener {

        val item: View = itemView

        val image: ImageView = binding.imageTopHeadlines
        val text: TextView = binding.titleTopHeadlines
        val date: TextView = binding.dateTopHeadlines

        override fun onClick(v: View?) {

        }

    }

}
