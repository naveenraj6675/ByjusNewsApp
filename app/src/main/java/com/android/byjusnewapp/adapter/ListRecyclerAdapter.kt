package com.android.byjusnewapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.android.byjusnewapp.R
import com.android.byjusnewapp.helpers.DateHelper
import com.android.byjusnewapp.models.Articles
import org.w3c.dom.Text

class ListRecyclerAdapter (var context : Context ,var mNewsList : ArrayList<Articles>): RecyclerView.Adapter<ListRecyclerAdapter.ViewHolder>() {

    var onItemClicked : ((Articles) -> Unit) ?= null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_news_list,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListRecyclerAdapter.ViewHolder, position: Int) {
        val item = mNewsList[position]

        holder.titleTV.text = item.title
        holder.authorNameTV.text = item.author
        holder.publishDataTV.text = item.publishedAt?.let { DateHelper.getPublisherDateFromAPi(it) }
        holder.backgroundIV.load(item.imageUrl)

    }

    override fun getItemCount(): Int {
       return mNewsList.size
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val titleTV :  TextView = view.findViewById(R.id.title_tv)
        var authorNameTV : TextView = view.findViewById(R.id.author_name_tv)
        var publishDataTV : TextView = view.findViewById(R.id.publish_date_tv)
        var backgroundIV : ImageView = view.findViewById(R.id.card_bg)

        init {
            view.setOnClickListener {
                onItemClicked?.invoke(mNewsList[adapterPosition])
            }
        }


    }
}