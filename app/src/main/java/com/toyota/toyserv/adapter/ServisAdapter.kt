package com.toyota.toyserv.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.toyota.toyserv.network.ApiClient
import com.toyota.toyserv.databinding.ItemServiceBinding
import com.toyota.toyserv.model.DataResponse
import com.toyota.toyserv.model.DataResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServisAdapter(
    private val servisList: ArrayList<DataResult>,

) :
    RecyclerView.Adapter<ServisAdapter.ListViewHolder>() {
    private lateinit var sharedPref: PreferencesHelper

    inner class ListViewHolder(private val binding: ItemServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataList: DataResult) {
            sharedPref = PreferencesHelper(itemView.context)
            val idUser = sharedPref.getString(Constant.PREF_IS_LOGIN_ID)

            binding.tvServiceName.text = dataList.name
            binding.tvDescription.text = dataList.description

            binding.btnRequestServis.setOnClickListener {
                requestService(it, dataList.id, idUser!!)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(servisList[position])
    }

    override fun getItemCount(): Int = servisList.size

    interface iUserRecycler {
        fun refreshView(idService: String, idUser: String, name: String)
    }

    private fun requestService(view: View, name: String, idUser: String) {
        ApiClient.instances.requestServicePost(
            "",
            name,
            idUser,
            "perbaiki dulu kanda",
            "",
            "",
            "",
            ""
        )
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