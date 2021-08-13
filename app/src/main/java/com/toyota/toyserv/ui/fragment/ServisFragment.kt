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
import com.toyota.toyserv.adapter.ServisAdapter
import com.toyota.toyserv.network.ApiClient
import com.toyota.toyserv.adapter.VehicleOperationAdapter
import com.toyota.toyserv.adapter.ViewPagerAdapter
import com.toyota.toyserv.databinding.FragmentServisBinding
import com.toyota.toyserv.model.DataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServisFragment : Fragment() {
    private var _binding: FragmentServisBinding? = null
    private val binding get() = _binding!!

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

        binding.parentRv.visibility = View.INVISIBLE
        binding.parentTabViewPager.visibility = View.VISIBLE
        val item = "servis"
        val totalItem = 2

        val viewPagerAdapter = ViewPagerAdapter(
            requireActivity().supportFragmentManager,
            lifecycle, item, totalItem
        )

        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Ringan"
                1 -> tab.text = "Berat"
            }
        }.attach()    }

    override fun onResume() {
        super.onResume()

    }
}