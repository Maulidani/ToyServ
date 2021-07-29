package com.toyota.toyserv.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toyota.toyserv.databinding.ItemBelumDijadwalkanServisBinding
import com.toyota.toyserv.model.DataResult

class ServisBelumDijadwalkanAdapter(
    private val belumDijadwalkanList: ArrayList<DataResult>
) :
    RecyclerView.Adapter<ServisBelumDijadwalkanAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: ItemBelumDijadwalkanServisBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataList: DataResult) {

            val userType = "customer"
            if (userType != "customer") {
                when (dataList.type) {
                    "sudah_dijadwalkan" -> binding.btnSelesai.visibility = View.VISIBLE
                    "belum_dijadwalkan" -> binding.btnJadwalkan.visibility = View.VISIBLE
                }
            }
            binding.tvServiceName.text = dataList.service_name
            binding.tvServiceType.text = dataList.type_service
            binding.tvVechile.text = dataList.vehicle
            binding.tvPemilik.text = dataList.user_name
            binding.tvNote.text = dataList.note
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemBelumDijadwalkanServisBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(belumDijadwalkanList[position])
    }

    override fun getItemCount(): Int = belumDijadwalkanList.size
}