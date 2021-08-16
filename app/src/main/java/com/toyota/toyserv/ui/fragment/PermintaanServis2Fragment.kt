@file:Suppress("DEPRECATION")

package com.toyota.toyserv.ui.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.toyota.toyserv.adapter.ServisBelumDijadwalkanAdapter
import com.toyota.toyserv.adapter.ServisSelesaiAdapter
import com.toyota.toyserv.adapter.ServisSudahDijadwalkanAdapter
import com.toyota.toyserv.databinding.FragmentPermintaanServis2Binding
import com.toyota.toyserv.model.DataResponse
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PermintaanServis2Fragment(_type: String) : Fragment(),
    ServisBelumDijadwalkanAdapter.iUserRecycler, ServisSelesaiAdapter.iUserRecycler,
    ServisSudahDijadwalkanAdapter.iUserRecycler {
    private val type = _type

    private var _binding: FragmentPermintaanServis2Binding? = null
    private val binding get() = _binding!!

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: ServisBelumDijadwalkanAdapter
    private lateinit var adapter2: ServisSudahDijadwalkanAdapter
    private lateinit var adapter3: ServisSelesaiAdapter

    private lateinit var progressDialog: ProgressDialog

    private lateinit var sharedPref: PreferencesHelper
    private lateinit var datePickerDialog: DatePickerDialog

    private lateinit var typeAkun: String
    private lateinit var idAkun: String

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPermintaanServis2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = PreferencesHelper(requireActivity())
        idAkun = sharedPref.getString(Constant.PREF_IS_LOGIN_ID).toString()
        typeAkun = sharedPref.getString(Constant.PREF_IS_LOGIN_TYPE).toString()

        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("Memuat Informasi...")
        progressDialog.setCancelable(false)

        layoutManager = LinearLayoutManager(requireActivity())
        binding.rv.layoutManager = layoutManager

        if (typeAkun == "admin") {
            servisSemua(type, "", "all")
        } else {
            servisSemua(type, idAkun, "cs")
        }
    }

    private fun servisSemua(type: String, idAkun: String, typeLogin: String) {
        progressDialog.show()

        ApiClient.instances.requestServiceGet(type, idAkun, typeLogin)
            .enqueue(object : Callback<DataResponse> {
                override fun onResponse(
                    call: Call<DataResponse>,
                    response: Response<DataResponse>
                ) {
                    val value = response.body()?.value
                    val result = response.body()?.result

                    if (response.isSuccessful && value == "1") {

                        when (type) {

                            "belum_dijadwalkan" -> {
                                adapter = ServisBelumDijadwalkanAdapter(
                                    result!!,
                                    this@PermintaanServis2Fragment
                                )
                                binding.rv.adapter = adapter
                                adapter.notifyDataSetChanged()

                            }
                            "sudah_dijadwalkan" -> {
                                adapter2 = ServisSudahDijadwalkanAdapter(result!!,this@PermintaanServis2Fragment)
                                binding.rv.adapter = adapter2
                                adapter2.notifyDataSetChanged()

                            }
                            "selesai" -> {
                                adapter3 =
                                    ServisSelesaiAdapter(result!!, this@PermintaanServis2Fragment)
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
                    progressDialog.dismiss()

                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    Toast.makeText(requireActivity(), "Failure", Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()

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

        setDateTimeField("jadwalkan")
        binding.xJadwalkan.setOnClickListener {
            binding.parentJadwalkan.visibility = View.INVISIBLE
        }

        binding.tvServiceName.text = serviceName
        binding.tvServiceType.text = typeService
        binding.tvVechile.text = vehicle
        binding.tvPemilik.text = userName

        binding.inputJadwal.setOnClickListener {
            datePickerDialog.show()
        }
        binding.btnJadwalkan.setOnClickListener {

            if (!binding.inputJadwal.text.toString().isEmpty()) {
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                val awal = SimpleDateFormat("dd MMMM yyyy")

                val date: Date? = awal.parse(binding.inputJadwal.text.toString())
                val tanggalConversi: String = simpleDateFormat.format(date!!)

                jadwalkan(idService, idCs, tanggalConversi)
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Pilih jadwal terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setDateTimeField(s: String) {
        val newCalendar = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(
            requireActivity(),
            { _, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate[year, monthOfYear] = dayOfMonth
                val sd = SimpleDateFormat("dd MMMM yyyy")
                val startDate = newDate.time
                val fdate = sd.format(startDate)

                if (s == "next") {
                    binding.inputJadwalNext.setText(fdate)
                } else {
                    binding.inputJadwal.setText(fdate)
                }
            }, newCalendar[Calendar.YEAR], newCalendar[Calendar.MONTH],
            newCalendar[Calendar.DAY_OF_MONTH]
        )
    }

    private fun jadwalkan(idService: String, cs: String, serviceAt: String) {
        progressDialog.show()
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

                        if (typeAkun == "admin") {
                            servisSemua(type, "", "all")
                        } else {
                            servisSemua(type, idAkun, "cs")
                        }

                    } else {
                        Toast.makeText(requireActivity(), message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }

                    progressDialog.dismiss()
                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    Toast.makeText(requireActivity(), t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                }

            })
    }

    override fun refreshView(
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
    ) {

        binding.parentJadwalkanNext.visibility = View.VISIBLE

        setDateTimeField("next")
        binding.xJadwalkanNext.setOnClickListener {
            binding.parentJadwalkanNext.visibility = View.INVISIBLE
        }

        binding.tvServiceNameNext.text = serviceName
        binding.tvServiceTypeNext.text = typeService
        binding.tvVechileNext.text = vehicle
        binding.tvPemilikNext.text = userName
        binding.tvNoteInfoNext.text = "Servis/jadwalkan Setiap "
        binding.tvNoteNext.text = month+" bulan"

        binding.inputJadwalNext.setOnClickListener {
            datePickerDialog.show()
        }
        binding.btnJadwalkanNext.setOnClickListener {
            if (!binding.inputJadwalNext.text.toString().isEmpty()) {
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                val awal = SimpleDateFormat("dd MMMM yyyy")

                val date: Date? = awal.parse(binding.inputJadwalNext.text.toString())
                val tanggalConversi: String = simpleDateFormat.format(date!!)

                jadwalkanNext(idService, idUser, idCs, tanggalConversi, id)
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Pilih jadwal terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun jadwalkanNext(
        idService: String,
        idUser: String,
        idCs: String,
        date: String,
        id: String
    ) {
        progressDialog.show()
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
                        Toast.makeText(requireActivity(), message.toString(), Toast.LENGTH_SHORT)
                            .show()
                        binding.parentJadwalkanNext.visibility = View.INVISIBLE

                        if (typeAkun == "admin") {
                            servisSemua(type, "", "all")
                        } else {
                            servisSemua(type, idAkun, "cs")
                        }

                    } else {
                        Toast.makeText(requireActivity(), message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                    progressDialog.dismiss()
                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    Toast.makeText(requireActivity(), t.message.toString(), Toast.LENGTH_SHORT)
                        .show()

                    progressDialog.dismiss()
                }

            })
    }

    override fun onResume() {
        super.onResume()
        if (typeAkun == "admin") {
            servisSemua(type, "", "all")
        } else {
            servisSemua(type, idAkun, "cs")
        }
    }

    override fun refreshView() {
        if (typeAkun == "admin") {
            servisSemua(type, "", "all")
        } else {
            servisSemua(type, idAkun, "cs")
        }
    }
}