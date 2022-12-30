package com.binar.finalproject14.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject14.data.api.response.transaction.Data
import com.binar.finalproject14.databinding.TicketBinding

class TicketBackAdapter (private var itemClick: TicketBackAdapter.ListTicketInterface)
    : RecyclerView.Adapter<TicketBackAdapter.ViewHolder>(){


    private val differCallback = object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(
            oldItem: Data,
            newItem: Data
        ): Boolean {
            return oldItem.id == oldItem.id
        }

        override fun areContentsTheSame(
            oldItem: Data,
            newItem: Data
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, differCallback)


    inner class ViewHolder(private val binding: TicketBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Data) {
            with(item) {
                binding.kelas.text = item.back?.classX.toString()
                binding.txtPassenger.text = item.passenger?.firstName.toString()
                binding.codeFlight.text = item.back?.code.toString()
                binding.txtClass.text = item.back?.classX.toString()
                binding.cityCodeOrigin.text = item.back?.origin?.cityCode.toString()
                binding.cityCodeDestination.text = item.back?.destination?.cityCode.toString()
                binding.txtDate.text = item.back?.departureDate.toString()
                binding.txtAirplane.text = item.back?.airplane?.airplaneName.toString()
                binding.txtPrice.text = item.back?.price.toString()

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ticket = differ.currentList[position]
        holder.bind(ticket)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data: List<Data>) {
        differ.submitList(data)
    }

    interface ListTicketInterface {
    }
}