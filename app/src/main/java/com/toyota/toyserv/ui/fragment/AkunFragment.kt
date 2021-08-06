package com.toyota.toyserv.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.google.android.material.tabs.TabLayoutMediator
import com.toyota.toyserv.adapter.ViewPagerAdapter
import com.toyota.toyserv.databinding.FragmentAkunBinding

class AkunFragment : Fragment() {
    private lateinit var sharedPref: PreferencesHelper

    private var _binding: FragmentAkunBinding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = PreferencesHelper(requireActivity())

        val item = "akun"

        val totalItem = 2
        val viewPagerAdapter = ViewPagerAdapter(
            requireActivity().supportFragmentManager,
            lifecycle, item, totalItem, ""
        )
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Customer"
                1 -> tab.text = "Customer Service"
            }
        }.attach()
    }
}