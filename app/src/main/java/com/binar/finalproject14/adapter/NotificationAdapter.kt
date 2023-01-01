package com.binar.finalproject14.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.finalproject14.data.api.response.notification.DataNotif
import com.binar.finalproject14.databinding.ListNotifBinding

class NotificationAdapter (private val itemClick: NotificationAdapter.ListNotificationInterface)
    : RecyclerView.Adapter<NotificationAdapter.ViewHolder>(){


    private val differCallback = object : DiffUtil.ItemCallback<DataNotif>() {
        override fun areItemsTheSame(
            oldItem: DataNotif,
            newItem: DataNotif
        ): Boolean {
            return oldItem.id == oldItem.id
        }

        override fun areContentsTheSame(
            oldItem: DataNotif,
            newItem: DataNotif
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, differCallback)


    inner class ViewHolder(private val binding: ListNotifBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataNotif) {
            with(item) {
                binding.txtNotif.text = item.message.toString()

                binding.card.setOnClickListener(){
                    item.id?.let { it1 -> itemClick.notif(it1) }
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListNotifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notif = differ.currentList[position]
        holder.bind(notif)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data: List<DataNotif>) {
        differ.submitList(data)
    }

    interface ListNotificationInterface {
        fun notif(id: Int)
    }
}