package com.toyota.toyserv.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.toyota.toyserv.network.ApiClient
import com.toyota.toyserv.adapter.ServisAdapter
import com.toyota.toyserv.databinding.FragmentServis2Binding
import com.toyota.toyserv.model.DataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Servis2Fragment(_type: String, _idVehicleOperation: String) : Fragment() {
    private val type = _type
    private val idVehicleOperation = _idVehicleOperation

    private var _binding: FragmentServis2Binding? = null
    private val binding get() = _binding!!

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: ServisAdapter

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServis2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(requireActivity())
        binding.rv.layoutManager = layoutManager

        servis(type, idVehicleOperation)
    }

    private fun servis(type: String, idVehicleOperation: String) {
        ApiClient.instances.service(type, idVehicleOperation)
            .enqueue(object : Callback<DataResponse> {
                override fun onResponse(
                    call: Call<DataResponse>,
                    response: Response<DataResponse>
                ) {
                    val value = response.body()?.value
                    val result = response.body()?.result

                    if (response.isSuccessful && value == "1") {
                        Toast.makeText(requireActivity(), "sukses", Toast.LENGTH_SHORT).show()
                        adapter = ServisAdapter(result!!)
                        binding.rv.adapter = adapter
                        adapter.notifyDataSetChanged()

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