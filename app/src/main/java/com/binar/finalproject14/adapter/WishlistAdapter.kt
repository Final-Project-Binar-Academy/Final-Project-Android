package com.binar.finalproject14.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject14.R
import com.binar.finalproject14.data.api.response.ticket.DataFlight
import com.binar.finalproject14.data.model.WishlistData
import com.binar.finalproject14.databinding.ListItemBinding

class WishlistAdapter (private val listFlight: List<WishlistData>) :
    RecyclerView.Adapter<WishlistAdapter.WishlistFlightViewHolder>() {

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


    class WishlistFlightViewHolder(var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WishlistData) {
            with(itemView) {
                binding.apply {

                    binding.departureDate.text = item.departure_date
                    binding.departureTime.text = item.departure_time
                    binding.codeIataFrom.text = item.codeIataFrom
                    binding.city1.text = item.city_origin
                    binding.duration.text = item.duration
                    binding.arrivalDate.text = item.arrival_date
                    binding.arrivalTime.text = item.arrival_time
                    binding.kelas.text = item.kelas
                    binding.codeIataTo.text = item.codeIataTo
                    binding.city2.text = item.city_destination
                    binding.company.text = item.company
                    binding.price.text = item.price
                    binding.btnKelas.text = item.kelas

                    binding.card.setOnClickListener{
                        var bund = Bundle()
                        item.id_wishlist?.let { it1 -> bund.putInt("id", it1) }
                        Navigation.findNavController(it)
                            .navigate(R.id.action_whislistFragment_to_detailFragment, bund)
                    }
                }
            }

        }
    }

    fun setData(data : List<DataFlight>){
        differ.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WishlistAdapter.WishlistFlightViewHolder =
        WishlistFlightViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: WishlistFlightViewHolder, position: Int) =
        holder.bind(listFlight[position])


    override fun getItemCount(): Int = listFlight.size
}