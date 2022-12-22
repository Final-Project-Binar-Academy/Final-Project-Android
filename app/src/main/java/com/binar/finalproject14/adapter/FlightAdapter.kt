package com.binar.finalproject14.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject14.data.api.response.ticket.DataFlight
import com.binar.finalproject14.databinding.ListItemBinding

class FlightAdapter (private val itemClick: (DataFlight) -> Unit) : RecyclerView.Adapter<FlightAdapter.ViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<DataFlight>(){
        override fun areItemsTheSame(
            oldItem: DataFlight,
            newItem: DataFlight
        ): Boolean {
            return oldItem.id == oldItem.id
        }

        override fun areContentsTheSame(
            oldItem: DataFlight,
            newItem: DataFlight
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, differCallback)

    class ViewHolder(private val binding: ListItemBinding, val itemClick: (DataFlight) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataFlight) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.dataBinding = item

                binding.departureDate.text = item.departureDate.toString()
                binding.departureTime.text = item.departureTime.toString()
                binding.arrivalDate.text = item.arrivalDate.toString()
                binding.arrivalTime.text = item.arrivalTime.toString()
                binding.kelas.text = item.classX.toString()
                binding.btnKelas.text = item.classX.toString()

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ticket = differ.currentList[position]
        holder.bind(ticket)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data : List<DataFlight>){
        differ.submitList(data)
    }

    interface ListTicketInterface {
        fun onItemClick(TicketDetail: DataFlight)
    }
}