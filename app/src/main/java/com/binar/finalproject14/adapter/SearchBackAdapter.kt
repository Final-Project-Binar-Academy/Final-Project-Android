package com.binar.finalproject14.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject14.data.api.response.search.TicketBack
import com.binar.finalproject14.databinding.ListItemBinding
import java.text.SimpleDateFormat
import java.util.*

class SearchBackAdapter (private var itemClick: SearchBackAdapter.ListSearchBackInterface) : RecyclerView.Adapter<SearchBackAdapter.ViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<TicketBack>(){
        override fun areItemsTheSame(
            oldItem: TicketBack,
            newItem: TicketBack
        ): Boolean {
            return oldItem.id == oldItem.id
        }

        override fun areContentsTheSame(
            oldItem: TicketBack,
            newItem: TicketBack
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TicketBack) {
            with(item) {

                var simpleDateFormat = SimpleDateFormat("E, dd LLL")
                var departure : Date? = item.departureDate
                var departureDate = simpleDateFormat.format(departure?.time).toString()

                var arrival : Date? = item.departureDate
                var arrivalDate = simpleDateFormat.format(arrival?.time).toString()

                binding.departureDate.text = departureDate
                binding.departureTime.text = item.departureTime.toString()
                binding.arrivalDate.text = arrivalDate
                binding.arrivalTime.text = item.arrivalTime.toString()
                binding.kelas.text = item.classX.toString()
                binding.btnKelas.text = item.classX.toString()
                binding.price.text = item.price.toString()
                binding.codeIataFrom.text = item.origin?.cityCode.toString()
                binding.city1.text = item.origin?.city.toString()
                binding.codeIataTo.text = item.destination?.cityCode.toString()
                binding.city2.text = item.destination?.city.toString()
                binding.company.text = item.airplane?.company?.companyName.toString()

                binding.card.setOnClickListener{
                    item.id?.let { it1 -> itemClick.onItemClickBack(it1) }
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ticket = differ.currentList[position]
        holder.bind(ticket)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data : List<TicketBack>){
        differ.submitList(data)
    }

    interface ListSearchBackInterface {
        fun onItemClickBack(id: Int)
    }
}