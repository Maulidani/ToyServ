package com.toyota.toyserv.adapter

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.toyota.toyserv.R
import com.toyota.toyserv.databinding.ItemSudahDijadwalkanServisBinding
import com.toyota.toyserv.model.DataResponse
import com.toyota.toyserv.model.DataResult
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ServisSudahDijadwalkanAdapter(
    private val sudahDijadwalkanList: ArrayList<DataResult>,
    private val mListener: iUserRecycler

) :
    RecyclerView.Adapter<ServisSudahDijadwalkanAdapter.ListViewHolder>() {

    private lateinit var sharedPref: PreferencesHelper

    inner class ListViewHolder(private val binding: ItemSudahDijadwalkanServisBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataList: DataResult) {
            sharedPref = PreferencesHelper(itemView.context)
            val typeAkun = sharedPref.getString(Constant.PREF_IS_LOGIN_TYPE)
            if (typeAkun == "customer_service") {
                binding.btnSelesai.visibility = View.VISIBLE

                binding.btnSelesai.setOnClickListener {
                    selesaikan(it, dataList.id)
                }
            }
            binding.tvServiceName.text = dataList.service_name
            binding.tvServiceType.text = dataList.type_service
            binding.tvVechile.text = dataList.vehicle
            binding.tvPemilik.text = dataList.user_name
            binding.tvCs.text = dataList.cs_name
            binding.tvServiceAt.text = dataList.service_at

            if (dataList.expendable) {
                binding.parentDetails.visibility = View.VISIBLE
                binding.icDetails.setImageResource(R.drawable.ic_up)
            } else {
                binding.parentDetails.visibility = View.GONE
                binding.icDetails.setImageResource(R.drawable.ic_down)
            }
            binding.parentNameList.setOnClickListener {
                dataList.expendable = !dataList.expendable
                notifyDataSetChanged()
            }
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

    private fun selesaikan(view: View, idService: String) {
        ApiClient.instances.requestServicePost(idService, "", "", "", "", "", "selesai", "")
            .enqueue(object : Callback<DataResponse> {
                override fun onResponse(
                    call: Call<DataResponse>,
                    response: Response<DataResponse>
                ) {
                    val value = response.body()?.value
                    val message = response.body()?.message

                    if (response.isSuccessful && value == "1") {
                        Toast.makeText(view.context, message.toString(), Toast.LENGTH_SHORT).show()
                        mListener.refreshView()
                    } else {
                        Toast.makeText(view.context, message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    Toast.makeText(view.context, t.message.toString(), Toast.LENGTH_SHORT).show()
                }

            })
    }

    interface iUserRecycler {
        fun refreshView(
        )
    }


}