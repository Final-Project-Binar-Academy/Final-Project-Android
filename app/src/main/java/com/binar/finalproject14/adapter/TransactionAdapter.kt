package com.binar.finalproject14.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject14.data.api.response.transaction.Data
import com.binar.finalproject14.databinding.ListTiketUpcomingBinding

class TransactionAdapter (private var itemClick: TransactionAdapter.ListTransactionInterface)
    : RecyclerView.Adapter<TransactionAdapter.ViewHolder>(){


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


    inner class ViewHolder(private val binding: ListTiketUpcomingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Data) {
            with(item) {

                val cityCodeOrigin: String? = item.go?.origin?.cityCode
                var cityNameOrigin: String? = item.go?.origin?.city
                var airPortNameOrigin: String? = item.go?.origin?.airportName
                var departureTimeOrigin: String? = item.go?.departureTime
                var departureDateOrigin: String? = item.go?.departureDate
                var finaltimeOrigin = departureTimeOrigin?.subSequence(0, 5).toString()
                var finalDateOrigin = departureDateOrigin?.subSequence(0, 9).toString()
                var finalTimeAndDateOrigin = "$finaltimeOrigin $finalDateOrigin"

                var cityCodeTo: String? = item.go?.destination?.cityCode
                var cityNameTo: String? = item.go?.destination?.city
                var airPortNameTo: String? = item.go?.destination?.airportName
                var departureTimeTo: String? = item.go?.arrivalTime
                var departureDateTo: String? = item.go?.arrivalDate
                var finaltimeTo = departureTimeTo?.subSequence(0, 5).toString()
                var finalDateTo = departureDateTo?.subSequence(0, 9).toString()
                var finalTimeAndDateTo = "$finaltimeTo $finalDateTo"

                binding.txtDari.text = cityCodeOrigin
                binding.txtTempat.text = cityNameOrigin
                binding.tanggal.text = finalTimeAndDateOrigin
                binding.terminal.text = airPortNameOrigin

                binding.txtKe.text = cityCodeTo
                binding.txtNama.text = cityNameTo
                binding.tanggal2.text = finalTimeAndDateTo
                binding.terminal2.text = airPortNameTo

                binding.btnTicket.setOnClickListener(){
                    item.id?.let { it1 -> itemClick.ticket(it1) }
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListTiketUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flight = differ.currentList[position]
        holder.bind(flight)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data: List<Data>) {
        differ.submitList(data)
    }



    interface ListTransactionInterface {
        fun ticket(id: Int)
    }
}