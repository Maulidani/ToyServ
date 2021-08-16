@file:Suppress("DEPRECATION")

package com.toyota.toyserv.ui.fragment

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import apotekku.projectapotekku.utils.PreferencesHelper
import com.toyota.toyserv.adapter.AkunAdapter
import com.toyota.toyserv.databinding.FragmentAkun2Binding
import com.toyota.toyserv.model.DataResponse
import com.toyota.toyserv.network.ApiClient
import com.toyota.toyserv.ui.activity.RegisterActivity
import com.toyota.toyserv.ui.activity.RegisterCsActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Akun2Fragment(_type: String) : Fragment(), AkunAdapter.iUserRecycler {
    private lateinit var sharedPref: PreferencesHelper
    val type = _type
    private var _binding: FragmentAkun2Binding? = null
    private val binding get() = _binding!!

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: AkunAdapter

    private lateinit var progressDialog: ProgressDialog

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAkun2Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = PreferencesHelper(requireActivity())
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("Memuat Informasi...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        layoutManager = LinearLayoutManager(requireActivity())
        binding.rv.layoutManager = layoutManager

        binding.fabAdd.setOnClickListener {
            Toast.makeText(requireActivity(), "click add", Toast.LENGTH_SHORT).show()
        }
        getAkun(type)

        binding.fabAdd.setOnClickListener {
            if (type == "customer") {
                startActivity(Intent(requireActivity(), RegisterActivity::class.java))
            } else {
                startActivity(Intent(requireActivity(), RegisterCsActivity::class.java))
            }
        }
    }

    private fun getAkun(userType: String) {
        ApiClient.instances.getAkun(userType).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                val value = response.body()?.value
                val message = response.body()?.message
                val result = response.body()?.result

                if (response.isSuccessful && value == "1") {
                    adapter = AkunAdapter(result!!, userType,this@Akun2Fragment)
                    binding.rv.adapter = adapter
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
                }
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Toast.makeText(requireActivity(), t.message.toString(), Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getAkun(type)

    }

    override fun refreshView() {
        getAkun(type)
    }
}