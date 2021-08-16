package com.toyota.toyserv.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.toyota.toyserv.databinding.ItemAccountBinding
import com.toyota.toyserv.model.DataResponse
import com.toyota.toyserv.model.DataResult
import com.toyota.toyserv.network.ApiClient
import com.toyota.toyserv.ui.activity.RegisterActivity
import com.toyota.toyserv.ui.activity.RegisterCsActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AkunAdapter(
    private val accountList: ArrayList<DataResult>,
    userType: String,
    private val mListener: iUserRecycler
) :
    RecyclerView.Adapter<AkunAdapter.ListViewHolder>() {
    private lateinit var sharedPref: PreferencesHelper
    val type = userType

    inner class ListViewHolder(private val binding: ItemAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataList: DataResult) {
            sharedPref = PreferencesHelper(itemView.context)
            val userType = sharedPref.getString(Constant.PREF_IS_LOGIN_TYPE)

            binding.tvName.text = dataList.name

            binding.cardAccount.setOnClickListener {

                optionAlert(it, dataList)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(accountList[position])
    }

    override fun getItemCount(): Int = accountList.size

    private fun optionAlert(
        itemView: View,
        dataResult: DataResult
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(itemView.context)
        builder.setTitle("Aksi")

        val options = arrayOf("Edit akun", "Hapus akun")
        builder.setItems(
            options
        ) { _, which ->
            when (which) {
                0 -> {
                    if (type == "customer") {
                        itemView.context.startActivity(
                            Intent(itemView.context, RegisterActivity::class.java)
                                .putExtra("id", dataResult.id)
                                .putExtra("full_name", dataResult.name)
                                .putExtra("vehicle", dataResult.vehicle)
                                .putExtra("police_number", dataResult.police_number)
                                .putExtra("phone", dataResult.phone)
                                .putExtra("username", dataResult.username)
                                .putExtra("password", dataResult.password)
                                .putExtra("intent", true)
                        )
                    } else if (type == "customer_service") {
                        itemView.context.startActivity(
                            Intent(itemView.context, RegisterCsActivity::class.java)
                                .putExtra("id", dataResult.id)
                                .putExtra("full_name", dataResult.name)
                                .putExtra("username", dataResult.username)
                                .putExtra("password", dataResult.password)
                                .putExtra("intent", true)
                        )
                    }

                }
                1 -> deleteAlert(itemView, dataResult)
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun deleteAlert(itemView: View, dataResult: DataResult) {
        val builder = AlertDialog.Builder(itemView.context)
        builder.setTitle("Hapus")
        builder.setMessage("Hapus akun ?")

        builder.setPositiveButton("Ya") { _, _ ->
            delete(itemView, dataResult)
        }

        builder.setNegativeButton("Tidak") { _, _ ->
            // cancel
        }
        builder.show()
    }

    private fun delete(itemView: View, dataResult: DataResult) {

        ApiClient.instances.deleteAkccount(dataResult.id, type).enqueue(object :
            Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                val value = response.body()?.value
                val message = response.body()?.message

                if (response.isSuccessful && value == "1") {

                    Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
                    mListener.refreshView()
                } else {
                    Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Toast.makeText(itemView.context, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    interface iUserRecycler {
        fun refreshView()
    }
}