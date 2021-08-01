package com.toyota.toyserv.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toyota.toyserv.databinding.ItemSudahDijadwalkanServisBinding
import com.toyota.toyserv.model.DataResult

class ServisSudahDijadwalkanAdapter(
    private val sudahDijadwalkanList: ArrayList<DataResult>
) :
    RecyclerView.Adapter<ServisSudahDijadwalkanAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: ItemSudahDijadwalkanServisBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataList: DataResult) {

            val userType = "customer"
            if (userType == "customer_service") {
                binding.btnSelesai.visibility = View.VISIBLE
            }
            binding.tvServiceName.text = dataList.service_name
            binding.tvServiceType.text = dataList.type_service
            binding.tvVechile.text = dataList.vehicle
            binding.tvPemilik.text = dataList.user_name
            binding.tvCs.text = dataList.cs_name
            binding.tvServiceAt.text = dataList.service_at
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemSudahDijadwalkanServisBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(sudahDijadwalkanList[position])
    }

    override fun getItemCount(): Int = sudahDijadwalkanList.size
}