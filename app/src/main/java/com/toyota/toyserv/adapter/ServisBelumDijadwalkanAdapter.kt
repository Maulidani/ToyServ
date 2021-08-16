package com.toyota.toyserv.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.toyota.toyserv.R
import com.toyota.toyserv.databinding.ItemBelumDijadwalkanServisBinding
import com.toyota.toyserv.model.DataResponse
import com.toyota.toyserv.model.DataResult
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServisBelumDijadwalkanAdapter(
    private val belumDijadwalkanList: ArrayList<DataResult>,
    private val mListener: iUserRecycler
) :
    RecyclerView.Adapter<ServisBelumDijadwalkanAdapter.ListViewHolder>() {
    private lateinit var sharedPref: PreferencesHelper

    inner class ListViewHolder(private val binding: ItemBelumDijadwalkanServisBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataList: DataResult) {
            sharedPref = PreferencesHelper(itemView.context)

            val idService = dataList.id

            val userType = sharedPref.getString(Constant.PREF_IS_LOGIN_TYPE)
            val idCs = sharedPref.getString(Constant.PREF_IS_LOGIN_ID)

            if (userType == "customer_service") {
                binding.btnJadwalkan.visibility = View.VISIBLE
                binding.btnJadwalkan.setOnClickListener {
                    mListener.refreshView(
                        idService,
                        idCs!!,
                        dataList.service_name,
                        dataList.type_service,
                        dataList.vehicle,
                        dataList.user_name,
                        dataList.note
                    )
                }
            }
            binding.tvServiceName.text = dataList.service_name
            binding.tvServiceType.text = dataList.type_service
            binding.tvVechile.text = dataList.vehicle
            binding.tvPemilik.text = dataList.user_name
            binding.tvNote.text = dataList.note

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


    interface iUserRecycler {
        fun refreshView(
            idService: String,
            idCs: String,
            serviceName: String,
            typeService: String,
            vehicle: String,
            userName: String,
            note: String
        )
    }
}