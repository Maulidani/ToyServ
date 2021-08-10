@file:Suppress("DEPRECATION")

package com.toyota.toyserv.ui.fragment

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.toyota.toyserv.network.ApiClient
import com.toyota.toyserv.adapter.VehicleOperationAdapter
import com.toyota.toyserv.adapter.ViewPagerAdapter
import com.toyota.toyserv.databinding.FragmentServisBinding
import com.toyota.toyserv.model.DataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServisFragment : Fragment(), VehicleOperationAdapter.callback {
    private var _binding: FragmentServisBinding? = null
    private val binding get() = _binding!!

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: VehicleOperationAdapter
    private var servis:Boolean = false

    private lateinit var progressDialog: ProgressDialog

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(servis,"")
    }

    private fun getVehicleOperation() {
        ApiClient.instances.vehicleOperation().enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                val value = response.body()?.value
                val result = response.body()?.result

                if (response.isSuccessful && value == "1") {
                    Toast.makeText(requireActivity(), "sukses", Toast.LENGTH_SHORT).show()
                    adapter = VehicleOperationAdapter(result!!, this@ServisFragment)
                    binding.rv.adapter = adapter
                    adapter.notifyDataSetChanged()

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

    private fun init(servis: Boolean, id: String) {

        if (!servis) {
            progressDialog = ProgressDialog(requireActivity())
            progressDialog.setTitle("Loading")
            progressDialog.setMessage("Memuat Informasi...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            binding.parentRv.visibility = View.VISIBLE

            layoutManager = LinearLayoutManager(requireActivity())
            binding.rv.layoutManager = layoutManager

            getVehicleOperation()
        } else {

            binding.parentRv.visibility = View.INVISIBLE
            binding.parentTabViewPager.visibility = View.VISIBLE
            val item = "servis"
            val totalItem = 2
            val idVehicleOperation = id

            val viewPagerAdapter = ViewPagerAdapter(
                requireActivity().supportFragmentManager,
                lifecycle, item, totalItem, idVehicleOperation
            )

            binding.viewPager.adapter = viewPagerAdapter

            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "Ringan"
                    1 -> tab.text = "Berat"
                }
            }.attach()
        }

    }

    override fun refreshView(servis: Boolean, id: String) {
        this.servis = servis
        init(this.servis,id)
    }

    override fun onResume() {
        super.onResume()

    }
}