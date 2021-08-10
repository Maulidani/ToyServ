@file:Suppress("DEPRECATION")

package com.toyota.toyserv.ui.fragment

import android.app.ProgressDialog
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
import java.util.*

class Servis2Fragment(_type: String, _idVehicleOperation: String) : Fragment(),
    ServisAdapter.iUserRecycler {
    private val type = _type
    private val idVehicleOperation = _idVehicleOperation

    private var _binding: FragmentServis2Binding? = null
    private val binding get() = _binding!!

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: ServisAdapter
    private lateinit var progressDialog: ProgressDialog

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

        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Loading")
        progressDialog.setCancelable(false)

        layoutManager = LinearLayoutManager(requireActivity())
        binding.rv.layoutManager = layoutManager

        servis(type, idVehicleOperation)
    }

    private fun servis(type: String, idVehicleOperation: String) {
        progressDialog.show()

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
                        adapter = ServisAdapter(result!!, this@Servis2Fragment)
                        binding.rv.adapter = adapter
                        adapter.notifyDataSetChanged()

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

    override fun refreshView(idService: String, idUser: String, name: String) {
        binding.parentAddService.visibility = View.VISIBLE

        binding.tvService.text = name
        binding.xService.setOnClickListener {
            binding.parentAddService.visibility = View.INVISIBLE

        }
        binding.btnAdd.setOnClickListener {
            val note = binding.inputNote.text.toString()

            requestService(idService, idUser, note)
        }
    }

    private fun requestService(idService: String, idUser: String, note: String) {
        progressDialog.show()

        ApiClient.instances.requestServicePost(
            "",
            idService,
            idUser,
            note,
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
                        Toast.makeText(requireActivity(), message.toString(), Toast.LENGTH_SHORT)
                            .show()
                        binding.parentAddService.visibility = View.INVISIBLE
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
}