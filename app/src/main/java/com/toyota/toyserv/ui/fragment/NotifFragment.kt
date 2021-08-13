package com.toyota.toyserv.ui.fragment

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.toyota.toyserv.R
import com.toyota.toyserv.adapter.NotifAdapter
import com.toyota.toyserv.adapter.ServisAdapter
import com.toyota.toyserv.databinding.FragmentNotifBinding
import com.toyota.toyserv.databinding.FragmentServis2Binding
import com.toyota.toyserv.model.DataResponse
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotifFragment : Fragment() {
    private var _binding: FragmentNotifBinding? = null
    private val binding get() = _binding!!

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: NotifAdapter
    private lateinit var progressDialog: ProgressDialog

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotifBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Loading")
        progressDialog.setCancelable(false)

        layoutManager = LinearLayoutManager(requireActivity())
        binding.rv.layoutManager = layoutManager
        getNotif()
    }

    private fun getNotif() {
        ApiClient.instances.notification().enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                val value = response.body()?.value
                val result = response.body()?.result

                if (response.isSuccessful && value == "1") {
                    Toast.makeText(requireActivity(), "sukses", Toast.LENGTH_SHORT).show()
                    adapter = NotifAdapter(result!!)
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
}