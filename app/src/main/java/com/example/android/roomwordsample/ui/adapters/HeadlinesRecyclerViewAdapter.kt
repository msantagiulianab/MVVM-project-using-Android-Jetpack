package com.example.android.roomwordsample.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.android.roomwordsample.R
import com.example.android.roomwordsample.network.apiNews.NewsHeadlines
import com.example.android.roomwordsample.util.UtilMethods.convertISOTime

class HeadlinesRecyclerViewAdapter(var context: Context, var newsheadlines: List<NewsHeadlines>) :
    RecyclerView.Adapter<HeadlinesRecyclerViewAdapter.HeadlinesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlinesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_top_headlines, parent, false)
        return HeadlinesViewHolder(view)
    }

    override fun getItemCount(): Int = newsheadlines.size

    override fun onBindViewHolder(holder: HeadlinesViewHolder, position: Int) {

        if (newsheadlines.get(position).title != null) {
            holder.text.text = newsheadlines.get(position).title
        } else if (newsheadlines.get(position).description != null) {
            holder.text.text = newsheadlines.get(position).description
        } else if (newsheadlines.get(position).content != null) {
            holder.text.text = newsheadlines.get(position).content
        }

        Glide.with(context)
            .load(newsheadlines.get(position).urlToImage)
            .placeholder(R.drawable.index)
            .into(holder.image)

//        holder.item.setOnClickListener {
//            val intent = Intent(context, SingleNewsActivity::class.java);
//            intent.putExtra(
//                context.getString(R.string.content),
//                newsheadlines.get(position).content
//            )
//            intent.putExtra(
//                context.getString(R.string.description),
//                newsheadlines.get(position).description
//            )
//            intent.putExtra(context.getString(R.string.author), newsheadlines.get(position).author)
//            intent.putExtra(context.getString(R.string.url), newsheadlines.get(position).url)
//            intent.putExtra(
//                context.getString(R.string.urlToImage),
//                newsheadlines.get(position).urlToImage
//            )
//            intent.putExtra(context.getString(R.string.title), newsheadlines.get(position).title)
//            intent.putExtra(
//                context.getString(R.string.publishedAt),
//                newsheadlines.get(position).publishedAt
//            )
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            context.startActivity(intent)
//        }

        holder.date.text = convertISOTime(context, newsheadlines[position].publishedAt)

    }

    class HeadlinesViewHolder(itemView: View) : ViewHolder(itemView) {

        val image: ImageView = itemView.findViewById(R.id.image_top_headlines)
        val text: TextView = itemView.findViewById(R.id.title_top_headlines)
        val date: TextView = itemView.findViewById(R.id.date_top_headlines)
        val item: View = itemView

    }

}

