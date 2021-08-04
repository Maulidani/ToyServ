package com.toyota.toyserv.ui.fragment

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

class ServisSaya2Fragment(_type: String, _all: String) : Fragment() {
    private val type = _type
    private val all = _all

    private var _binding: FragmentServisSaya2Binding? = null
    private val binding get() = _binding!!

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: ServisBelumDijadwalkanAdapter
    private lateinit var adapter2: ServisSudahDijadwalkanAdapter
    private lateinit var adapter3: ServisSelesaiAdapter

    private lateinit var sharedPref: PreferencesHelper

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
                                adapter = ServisBelumDijadwalkanAdapter(result!!)
                                binding.rv.adapter = adapter
                                adapter.notifyDataSetChanged()

                            }
                            "sudah_dijadwalkan" -> {
                                adapter2 = ServisSudahDijadwalkanAdapter(result!!,"all")
                                binding.rv.adapter = adapter2
                                adapter2.notifyDataSetChanged()
                            }
                            "selesai" -> {
                                adapter3 = ServisSelesaiAdapter(result!!)
                                binding.rv.adapter = adapter3
                                adapter3.notifyDataSetChanged()
                            }
                            "dijadwalkan_cs" -> {
                                adapter2 = ServisSudahDijadwalkanAdapter(result!!,"")
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
                                adapter = ServisBelumDijadwalkanAdapter(result!!)
                                binding.rv.adapter = adapter
                                adapter.notifyDataSetChanged()

                            }
                            "sudah_dijadwalkan" -> {
                                adapter2 = ServisSudahDijadwalkanAdapter(result!!,"all")
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
}