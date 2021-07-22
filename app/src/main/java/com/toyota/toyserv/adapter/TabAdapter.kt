package com.toyota.toyserv.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.toyota.toyserv.ui.fragment.TypeServisFragment

class TabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, numberTabs: Int) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val tabCount = numberTabs

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> TypeServisFragment("tab 1")
            1 -> TypeServisFragment("tab 2")
            2 -> TypeServisFragment("tab 3")
            else -> return Fragment()
        }
    }
}