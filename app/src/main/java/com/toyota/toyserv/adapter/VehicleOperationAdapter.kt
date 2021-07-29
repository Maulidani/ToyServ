package com.toyota.toyserv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.toyota.toyserv.databinding.ItemVehicleOperationBinding
import com.toyota.toyserv.model.DataResult

class VehicleOperationAdapter(
    private val vehicleOperationList: ArrayList<DataResult>,
    private val mListener: callback
) :
    RecyclerView.Adapter<VehicleOperationAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: ItemVehicleOperationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataList: DataResult) {
            binding.tvName.text = dataList.name

            binding.card.setOnClickListener {
                Toast.makeText(it.context, dataList.id, Toast.LENGTH_SHORT).show()
                mListener.refreshView(true,dataList.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemVehicleOperationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(vehicleOperationList[position])
    }

    override fun getItemCount(): Int = vehicleOperationList.size

    interface callback {
        fun refreshView(servis: Boolean, id: String)
    }

}