package com.toyota.toyserv.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.toyota.toyserv.databinding.ItemAccountBinding
import com.toyota.toyserv.model.DataResult
import com.toyota.toyserv.ui.activity.RegisterActivity

class AkunAdapter(private val accountList: ArrayList<DataResult>) :
    RecyclerView.Adapter<AkunAdapter.ListViewHolder>() {
    private lateinit var sharedPref: PreferencesHelper

    inner class ListViewHolder(private val binding: ItemAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataList: DataResult) {
            sharedPref = PreferencesHelper(itemView.context)
            val userType = sharedPref.getString(Constant.PREF_IS_LOGIN_TYPE)

            binding.tvName.text = dataList.name

            binding.cardAccount.setOnClickListener {
                it.context.startActivity(
                    Intent(it.context, RegisterActivity::class.java)
                        .putExtra("full_name", dataList.name)
                        .putExtra("vehicle", dataList.vehicle)
                        .putExtra("police_number", dataList.police_number)
                        .putExtra("phone", dataList.phone)
                        .putExtra("username", dataList.username)
                        .putExtra("password", dataList.password)
                        .putExtra("intent", true)
                )
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

}