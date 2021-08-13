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
            val idAkun = sharedPref.getString(Constant.PREF_IS_LOGIN_ID)
            if (typeAkun == "customer_service") {

                if (dataList.next_at == "false") {
                    binding.btnJadwalkan.visibility = View.VISIBLE
                } else {
                    binding.btnJadwalkan.visibility = View.GONE
                }

                binding.btnJadwalkan.setOnClickListener {

                    if (fdate.isNullOrEmpty()) {
                        datePickerDialog.show()
                        binding.btnJadwalkan.text = "Kirim"

                    } else {

                        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                        val awal = SimpleDateFormat("dd MMMM yyyy")

                        val date: Date? = awal.parse(fdate.toString())
                        val tanggalConversi: String = simpleDateFormat.format(date!!)

                        if (binding.btnJadwalkan.text == "Kirim") {
                            jadwalkanNext(
                                it,
                                dataList.id_service,
                                dataList.id_user,
                                idAkun!!,
                                tanggalConversi,
                                dataList.id
                            )
                        } else {
                            binding.btnJadwalkan.text = "Jadwalkan"
                        }

                        fdate = null
                    }
                }

            }

            binding.tvServiceName.text = dataList.service_name
            binding.tvServiceType.text = dataList.type_service
            binding.tvVechile.text = dataList.vehicle
            binding.tvPemilik.text = dataList.user_name
            binding.tvCs.text = dataList.cs_name
            binding.tvServicedAt.text = dataList.finish_at
            binding.tvNextService.text = dataList.next_at

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

    private fun jadwalkanNext(
        view: View,
        idService: String,
        idUser: String,
        idCs: String,
        date: String,
        id: String
    ) {
        ApiClient.instances.requestServicePost(
            id,
            idService,
            idUser,
            "next",
            idCs,
            date,
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
                        Toast.makeText(view.context, message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(view.context, message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }

                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    Toast.makeText(view.context, t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }
}