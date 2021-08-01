package com.toyota.toyserv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toyota.toyserv.databinding.ItemFinishServiceBinding
import com.toyota.toyserv.model.DataResult

class ServisSelesaiAdapter(
    private val selesaiDijadwalkanList: ArrayList<DataResult>
) :
    RecyclerView.Adapter<ServisSelesaiAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: ItemFinishServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataList: DataResult) {

//            val userType = "customer"
//            if (userType == "customer_service") { }

            binding.tvServiceName.text = dataList.service_name
            binding.tvServiceType.text = dataList.type_service
            binding.tvVechile.text = dataList.vehicle
            binding.tvPemilik.text = dataList.user_name
            binding.tvCs.text = dataList.cs_name
            binding.tvServicedAt.text = dataList.finish_at
            binding.tvNextService.text = dataList.next_at
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemFinishServiceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(selesaiDijadwalkanList[position])
    }

    override fun getItemCount(): Int = selesaiDijadwalkanList.size
}