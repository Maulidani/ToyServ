package com.toyota.toyserv.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.toyota.toyserv.adapter.AkunAdapter
import com.toyota.toyserv.databinding.FragmentAkun2Binding
import com.toyota.toyserv.model.DataResponse
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Akun2Fragment(_type: String) : Fragment() {
    private lateinit var sharedPref: PreferencesHelper
    val type = _type
    private var _binding: FragmentAkun2Binding? = null
    private val binding get() = _binding!!

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: AkunAdapter

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

        layoutManager = LinearLayoutManager(requireActivity())
        binding.rv.layoutManager = layoutManager

        getAkun(type)
    }

    private fun getAkun(userType: String) {
        ApiClient.instances.getAkun(userType).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                val value = response.body()?.value
                val message = response.body()?.message
                val result = response.body()?.result

                if (response.isSuccessful && value == "1") {
                    adapter = AkunAdapter(result!!)
                    binding.rv.adapter = adapter
                    adapter.notifyDataSetChanged()
                    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Toast.makeText(requireActivity(), t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}