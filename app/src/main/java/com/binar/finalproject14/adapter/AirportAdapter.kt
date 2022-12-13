package com.binar.finalproject14.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject14.data.api.response.airport.DataAirport
import com.binar.finalproject14.databinding.ListItemBinding

class AirportAdapter(private val onItemClick: OnClickListener) :
    RecyclerView.Adapter<AirportAdapter.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<DataAirport>() {
        override fun areItemsTheSame(oldItem: DataAirport, newItem: DataAirport): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: DataAirport,
            newItem: DataAirport
        ): Boolean = oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(value: List<DataAirport>?) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirportAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ListItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: AirportAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataAirport) {
//            binding.apply {
//                tvAirport.text = data.airportName
//                tvPrice.text = data.price.toString()
//                root.setOnClickListener {
//                    onItemClick.onClickItem(data)
//                }
//            }
        }
    }

    interface OnClickListener {
        fun onClickItem(data: DataAirport)
    }
}