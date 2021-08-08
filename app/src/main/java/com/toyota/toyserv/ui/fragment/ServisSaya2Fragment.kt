package com.toyota.toyserv.ui.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.toyota.toyserv.network.ApiClient
import com.toyota.toyserv.adapter.ServisBelumDijadwalkanAdapter
import com.toyota.toyserv.adapter.ServisSelesaiAdapter
import com.toyota.toyserv.adapter.ServisSudahDijadwalkanAdapter
import com.toyota.toyserv.databinding.FragmentServisSaya2Binding
import com.toyota.toyserv.model.DataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ServisSaya2Fragment(_type: String, _all: String) : Fragment(),
    ServisBelumDijadwalkanAdapter.iUserRecycler {
    private val type = _type
    private val all = _all

    private var _binding: FragmentServisSaya2Binding? = null
    private val binding get() = _binding!!

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: ServisBelumDijadwalkanAdapter
    private lateinit var adapter2: ServisSudahDijadwalkanAdapter
    private lateinit var adapter3: ServisSelesaiAdapter

    private lateinit var sharedPref: PreferencesHelper

    private lateinit var datePickerDialog: DatePickerDialog

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentServisSaya2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = PreferencesHelper(requireActivity())

        layoutManager = LinearLayoutManager(requireActivity())
        binding.rv.layoutManager = layoutManager

        val idAkun = sharedPref.getString(Constant.PREF_IS_LOGIN_ID)

        if (all == "all") {
            servisSemua(type)
        } else {
            servis(type, idAkun!!)
        }
    }

    private fun servis(type: String, idAkun: String) {
        ApiClient.instances.requestServiceGet(type, idAkun)
            .enqueue(object : Callback<DataResponse> {
                override fun onResponse(
                    call: Call<DataResponse>,
                    response: Response<DataResponse>
                ) {
                    val value = response.body()?.value
                    val result = response.body()?.result

                    if (response.isSuccessful && value == "1") {
                        Toast.makeText(requireActivity(), "sukses", Toast.LENGTH_SHORT).show()

                        when (type) {

                            "belum_dijadwalkan" -> {
                                adapter = ServisBelumDijadwalkanAdapter(
                                    result!!,
                                    this@ServisSaya2Fragment
                                )
                                binding.rv.adapter = adapter
                                adapter.notifyDataSetChanged()

                            }
                            "sudah_dijadwalkan" -> {
                                adapter2 = ServisSudahDijadwalkanAdapter(result!!, "all")
                                binding.rv.adapter = adapter2
                                adapter2.notifyDataSetChanged()
                            }
                            "selesai" -> {
                                adapter3 = ServisSelesaiAdapter(result!!)
                                binding.rv.adapter = adapter3
                                adapter3.notifyDataSetChanged()
                            }
                            "dijadwalkan_cs" -> {
                                adapter2 = ServisSudahDijadwalkanAdapter(result!!, "")
                                binding.rv.adapter = adapter2
                                adapter2.notifyDataSetChanged()
                            }
                            "selesai_cs" -> {
                                adapter3 = ServisSelesaiAdapter(result!!)
                                binding.rv.adapter = adapter3
                                adapter3.notifyDataSetChanged()
                            }
                        }

                        if (result.isNullOrEmpty()) {
                            Toast.makeText(requireActivity(), "Data Kosong", Toast.LENGTH_SHORT)
                                .show()
                        }

                    } else {
                        Toast.makeText(requireActivity(), "not Succes", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    Toast.makeText(requireActivity(), "Failure", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun servisSemua(type: String) {
        ApiClient.instances.requestServiceGet(type, "")
            .enqueue(object : Callback<DataResponse> {
                override fun onResponse(
                    call: Call<DataResponse>,
                    response: Response<DataResponse>
                ) {
                    val value = response.body()?.value
                    val result = response.body()?.result

                    if (response.isSuccessful && value == "1") {
                        Toast.makeText(requireActivity(), "sukses", Toast.LENGTH_SHORT).show()

                        when (type) {

                            "belum_dijadwalkan" -> {
                                adapter = ServisBelumDijadwalkanAdapter(
                                    result!!,
                                    this@ServisSaya2Fragment
                                )
                                binding.rv.adapter = adapter
                                adapter.notifyDataSetChanged()

                            }
                            "sudah_dijadwalkan" -> {
                                adapter2 = ServisSudahDijadwalkanAdapter(result!!, "all")
                                binding.rv.adapter = adapter2
                                adapter2.notifyDataSetChanged()

                            }
                            "selesai" -> {
                                adapter3 = ServisSelesaiAdapter(result!!)
                                binding.rv.adapter = adapter3
                                adapter3.notifyDataSetChanged()
                            }

                        }

                        if (result.isNullOrEmpty()) {
                            Toast.makeText(requireActivity(), "Data Kosong", Toast.LENGTH_SHORT)
                                .show()
                        }

                    } else {
                        Toast.makeText(requireActivity(), "not Succes", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    Toast.makeText(requireActivity(), "Failure", Toast.LENGTH_SHORT).show()
                }
            })
    }

    @SuppressLint("SimpleDateFormat")
    override fun refreshView(
        idService: String,
        idCs: String,
        serviceName: String,
        typeService: String,
        vehicle: String,
        userName: String,
        note: String
    ) {

        binding.parentJadwalkan.visibility = View.VISIBLE

        setDateTimeField()
        binding.xJadwalkan.setOnClickListener {
            binding.parentJadwalkan.visibility = View.INVISIBLE
        }

        binding.tvServiceName.text = serviceName
        binding.tvServiceType.text = typeService
        binding.tvVechile.text = vehicle
        binding.tvPemilik.text = userName
        binding.tvNote.text = note

        binding.inputJadwal.setOnClickListener {
            datePickerDialog.show()
        }
        binding.btnJadwalkan.setOnClickListener {

            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val awal = SimpleDateFormat("dd MMMM yyyy")

            val date: Date? = awal.parse(binding.inputJadwal.text.toString())
            val tanggalConversi: String = simpleDateFormat.format(date!!)

            jadwalkan(idService, idCs, tanggalConversi)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setDateTimeField() {
        val newCalendar = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(
            requireActivity(),
            { _, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate[year, monthOfYear] = dayOfMonth
                val sd = SimpleDateFormat("dd MMMM yyyy")
                val startDate = newDate.time
                val fdate = sd.format(startDate)
                binding.inputJadwal.setText(fdate)
            }, newCalendar[Calendar.YEAR], newCalendar[Calendar.MONTH],
            newCalendar[Calendar.DAY_OF_MONTH]
        )
    }

    private fun jadwalkan(idService: String, cs: String, serviceAt: String) {
        ApiClient.instances.requestServicePost(idService, "", "", "", cs, serviceAt, "", "")
            .enqueue(object : Callback<DataResponse> {
                override fun onResponse(
                    call: Call<DataResponse>,
                    response: Response<DataResponse>
                ) {
                    val value = response.body()?.value
                    val message = response.body()?.message

                    if (response.isSuccessful && value == "1") {
                        Toast.makeText(requireActivity(), message.toString(), Toast.LENGTH_SHORT)
                            .show()
                        binding.parentJadwalkan.visibility = View.INVISIBLE
                    } else {
                        Toast.makeText(requireActivity(), message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    Toast.makeText(requireActivity(), t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }

}