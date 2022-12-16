package com.binar.finalproject14.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject14.data.api.response.airport.AirportResponse
import com.binar.finalproject14.data.api.response.airport.DataAirport
import com.binar.finalproject14.databinding.ListItemBinding


class AirportAdapter (private val itemClick: (DataAirport) -> Unit) : RecyclerView.Adapter<AirportAdapter.ViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<DataAirport>(){
        override fun areItemsTheSame(
            oldItem: DataAirport,
            newItem: DataAirport
        ): Boolean {
            return oldItem.id == oldItem.id
        }

        override fun areContentsTheSame(
            oldItem: DataAirport,
            newItem: DataAirport
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, differCallback)

    class ViewHolder(private val binding: ListItemBinding, val itemClick: (DataAirport) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataAirport) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.dataBinding = item


            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = differ.currentList[position]
        holder.bind(film)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data : List<DataAirport>){
        differ.submitList(data)
    }

    interface ListMovieInterface {
        fun onItemClick(_airport: DataAirport)
    }
}