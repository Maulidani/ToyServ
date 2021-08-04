package com.toyota.toyserv.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.toyota.toyserv.databinding.ItemBelumDijadwalkanServisBinding
import com.toyota.toyserv.model.DataResponse
import com.toyota.toyserv.model.DataResult
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServisBelumDijadwalkanAdapter(
    private val belumDijadwalkanList: ArrayList<DataResult>
) :
    RecyclerView.Adapter<ServisBelumDijadwalkanAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: ItemBelumDijadwalkanServisBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataList: DataResult) {

            val idService = "14"
            val cs = "3"
            val serviceAt = "2066"
            val userType = "customer_service"
            if (userType == "customer_service") {
                binding.btnJadwalkan.visibility = View.VISIBLE
                binding.btnJadwalkan.setOnClickListener {
                    jadwalkan(it, idService, cs, serviceAt)
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

    private fun jadwalkan(view: View, idService: String, cs: String, serviceAt: String) {
        ApiClient.instances.requestServicePost(idService, "", "", "", cs, serviceAt, "", "")
            .enqueue(object : Callback<DataResponse> {
                override fun onResponse(
                    call: Call<DataResponse>,
                    response: Response<DataResponse>
                ) {
                    val value = response.body()?.value
                    val message = response.body()?.message

                    if (response.isSuccessful && value == "1") {
                        Toast.makeText(view.context, message.toString(), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(view.context, message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    Toast.makeText(view.context, t.message.toString(), Toast.LENGTH_SHORT).show()
                }

            })
    }


}