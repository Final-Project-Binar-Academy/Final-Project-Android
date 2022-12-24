package com.binar.finalproject14.adapter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject14.R
import com.binar.finalproject14.data.api.response.airport.DataAirport
import com.binar.finalproject14.databinding.FragmentHomeBinding
import com.binar.finalproject14.databinding.FragmentListViewBinding
import com.binar.finalproject14.databinding.ListItemBinding
import java.util.*

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

    class ViewHolder(private val binding: FragmentHomeBinding, val itemClick: (DataAirport) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataAirport) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }

                binding.btnDeparture.setOnClickListener {
                    val mCities = arrayListOf(item.city)
                    var bund = Bundle()
                    item.id?.let { it1 -> bund.putStringArrayList("mCities", mCities) }
                    findNavController(it).navigate(
                        R.id.action_homeFragment_to_listViewFragment,
                        bund
                    )
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val airport = differ.currentList[position]
        holder.bind(airport)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data : List<DataAirport>){
        differ.submitList(data)
    }

    interface ListAirportInterface {
        fun onItemClick(AirportDetail: DataAirport)
    }
}