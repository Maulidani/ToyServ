package com.toyota.toyserv.adapter

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.toyota.toyserv.R
import com.toyota.toyserv.databinding.ItemFinishServiceBinding
import com.toyota.toyserv.model.DataResponse
import com.toyota.toyserv.model.DataResult
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ServisSelesaiAdapter(
    private val selesaiDijadwalkanList: ArrayList<DataResult>,
    private val mListener: iUserRecycler
) :
    RecyclerView.Adapter<ServisSelesaiAdapter.ListViewHolder>() {
    private lateinit var sharedPref: PreferencesHelper
    private lateinit var datePickerDialog: DatePickerDialog
    private var fdate: String? = null

    inner class ListViewHolder(private val binding: ItemFinishServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SimpleDateFormat")
        fun bind(dataList: DataResult) {
            setDateTimeField(itemView)
            sharedPref = PreferencesHelper(itemView.context)
            val typeAkun = sharedPref.getString(Constant.PREF_IS_LOGIN_TYPE)
            val idCs = sharedPref.getString(Constant.PREF_IS_LOGIN_ID)
            if (typeAkun == "customer_service") {

                if (dataList.next_at == "false") {
                    binding.btnJadwalkan.visibility = View.VISIBLE
                } else {
                    binding.btnJadwalkan.visibility = View.GONE
                }

                binding.btnJadwalkan.setOnClickListener {
                    mListener.refreshView(
                        dataList.id,
                        idCs!!,
                        dataList.service_name,
                        dataList.type_service,
                        dataList.vehicle,
                        dataList.user_name,
                        dataList.note,
                        dataList.id_service,
                        dataList.id_user,
                        dataList.month
                    )
                }

            }

            binding.tvServiceName.text = dataList.service_name
            binding.tvServiceType.text = dataList.type_service
            binding.tvVechile.text = dataList.vehicle
            binding.tvPemilik.text = dataList.user_name
            binding.tvCs.text = dataList.cs_name
            binding.tvServicedAt.text = dataList.finish_at
            if (dataList.next_at == "false") {
                binding.tvNextService.text = "Belum dijadwalkan"
            } else {
                binding.tvNextService.text = dataList.next_at
            }

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

        @SuppressLint("SimpleDateFormat")
        private fun setDateTimeField(itemView: View) {
            val newCalendar = Calendar.getInstance()
            datePickerDialog = DatePickerDialog(
                itemView.context,
                { _, year, monthOfYear, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    newDate[year, monthOfYear] = dayOfMonth
                    val sd = SimpleDateFormat("dd MMMM yyyy")
                    val startDate = newDate.time
                    fdate = sd.format(startDate)
                    binding.tvNextService.text = fdate.toString()
                }, newCalendar[Calendar.YEAR], newCalendar[Calendar.MONTH],
                newCalendar[Calendar.DAY_OF_MONTH]
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemFinishServiceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(selesaiDijadwalkanList[position])

    }

    override fun getItemCount(): Int = selesaiDijadwalkanList.size

    interface iUserRecycler {
        fun refreshView(
            id: String,
            idCs: String,
            serviceName: String,
            typeService: String,
            vehicle: String,
            userName: String,
            note: String,
            idService: String,
            idUser: String,
            month: String
        )
    }
}