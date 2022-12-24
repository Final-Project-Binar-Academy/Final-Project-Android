package com.binar.finalproject14.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.binar.finalproject14.R
import com.binar.finalproject14.data.api.response.Article
import com.binar.finalproject14.databinding.ListImportantInfoBinding

class InfoAdapter (private val itemClick: (Article) -> Unit) : RecyclerView.Adapter<InfoAdapter.ViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean {
            return oldItem.title == oldItem.title
        }

        override fun areContentsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, differCallback)

    class ViewHolder(private val binding: ListImportantInfoBinding, val itemClick: (Article) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Article) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.dataBinding = item

                binding.imgFilm.load(item.urlToImage) {
                    crossfade(true)
                    placeholder(R.drawable.img_placeholder)
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListImportantInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ticket = differ.currentList[position]
        holder.bind(ticket)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data : List<Article>){
        differ.submitList(data)
    }

    interface ListInfoInterface {
        fun onItemClick(InfoDetail: Article)
    }
}