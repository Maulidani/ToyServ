package com.toyota.toyserv.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.toyota.toyserv.databinding.ItemSudahDijadwalkanServisBinding
import com.toyota.toyserv.model.DataResponse
import com.toyota.toyserv.model.DataResult
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServisSudahDijadwalkanAdapter(
    private val sudahDijadwalkanList: ArrayList<DataResult>,
    _all: String
) :
    RecyclerView.Adapter<ServisSudahDijadwalkanAdapter.ListViewHolder>() {

    val all = _all
    private lateinit var sharedPref: PreferencesHelper

    inner class ListViewHolder(private val binding: ItemSudahDijadwalkanServisBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataList: DataResult) {
            sharedPref = PreferencesHelper(itemView.context)

            val idService = dataList.id

            val userType = sharedPref.getString(Constant.PREF_IS_LOGIN_TYPE)

            if (userType == "customer_service") {
                if (all != "all") {
                    binding.btnSelesai.visibility = View.VISIBLE

                    binding.btnSelesai.setOnClickListener {
                        selesaikan(it, idService, "2018-08-08", "2018-08-08")
                    }
                }
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

    private fun selesaikan(view: View, idService: String, finishAt: String, nextAt: String) {
        ApiClient.instances.requestServicePost(idService, "", "", "", "", "", finishAt, nextAt)
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