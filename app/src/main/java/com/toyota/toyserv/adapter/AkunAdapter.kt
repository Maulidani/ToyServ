package com.toyota.toyserv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.toyota.toyserv.databinding.ItemAccountBinding
import com.toyota.toyserv.model.DataResult

class AkunAdapter(private val accountList: ArrayList<DataResult>) :
    RecyclerView.Adapter<AkunAdapter.ListViewHolder>() {
    private lateinit var sharedPref: PreferencesHelper

    inner class ListViewHolder(private val binding: ItemAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataList: DataResult) {
            sharedPref = PreferencesHelper(itemView.context)
            val userType = sharedPref.getString(Constant.PREF_IS_LOGIN_TYPE)

            binding.tvName.text = dataList.name

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