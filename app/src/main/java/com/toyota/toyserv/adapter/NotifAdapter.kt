package com.toyota.toyserv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toyota.toyserv.databinding.ItemNotifBinding
import com.toyota.toyserv.databinding.ItemServiceBinding
import com.toyota.toyserv.model.DataResult

class NotifAdapter(
    private val notifList: ArrayList<DataResult>
) :
    RecyclerView.Adapter<NotifAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: ItemNotifBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataList: DataResult) {

            binding.tvBody.text = dataList.body
            binding.tvCustomer.text = dataList.customer
            binding.tvDate.text = dataList.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifAdapter.ListViewHolder {
        val binding =
            ItemNotifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotifAdapter.ListViewHolder, position: Int) {
        holder.bind(notifList[position])
    }

    override fun getItemCount(): Int = notifList.size
}