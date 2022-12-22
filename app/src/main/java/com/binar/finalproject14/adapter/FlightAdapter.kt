package com.binar.finalproject14.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject14.R
import com.binar.finalproject14.data.api.response.ticket.DataFlight
import com.binar.finalproject14.databinding.ListItemBinding
import java.text.SimpleDateFormat
import java.util.*


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

                var simpleDateFormat = SimpleDateFormat("E, dd LLL")
                var departure : Date? = item.departureDate
                var departureDate = simpleDateFormat.format(departure?.time).toString()

                var arrival : Date? = item.arrivalDate
                var arrivalDate = simpleDateFormat.format(arrival?.time).toString()

                binding.departureDate.text = departureDate
                binding.departureTime.text = item.departureTime.toString()
                binding.arrivalDate.text = arrivalDate
                binding.arrivalTime.text = item.arrivalTime.toString()
                binding.kelas.text = item.classX.toString()
                binding.btnKelas.text = item.classX.toString()
                binding.price.text = item.price.toString()

                binding.card.setOnClickListener{
                    var bund = Bundle()
                    item.id?.let { it1 -> bund.putInt("id", it1) }
                    findNavController(it).navigate(R.id.action_searchFragment_to_detailFragment, bund)
                }

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