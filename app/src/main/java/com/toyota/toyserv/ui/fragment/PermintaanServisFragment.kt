package com.toyota.toyserv.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.toyota.toyserv.adapter.TabAdapter
import com.toyota.toyserv.databinding.FragmentPermintaanServisBinding

class PermintaanServisFragment : Fragment() {
    private var _binding: FragmentPermintaanServisBinding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPermintaanServisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabAdapter = TabAdapter(
            requireActivity().supportFragmentManager, requireActivity().lifecycle,
            binding.tabLayout.tabCount
        )
        binding.viewPager.adapter = tabAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "tab 1"
                1 -> tab.text = "tab 2"
                2 -> tab.text = "tab 3"
            }
        }.attach()
    }
}