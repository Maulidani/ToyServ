package com.toyota.toyserv.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.toyota.toyserv.ui.fragment.Servis2Fragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    itemNav: String,
    totalItem: Int
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val item = itemNav
    private val total = totalItem

    override fun getItemCount(): Int = total

    override fun createFragment(position: Int): Fragment {

        if (item == "servis") {
            return when (position) {
                0 -> Servis2Fragment("ringan")
                1 -> Servis2Fragment("berat")
                else -> Fragment()
            }
        }
//        else if (item == "Downline") {
//            return when (position) {
//                0 -> DownlineItemFragment("list downline")
//                1 -> DownlineItemFragment("list komisi")
//                2 -> DownlineItemFragment("penarikan")
//                else -> Fragment()
//            }
//        } else if (item == "Info") {
//
//        }
        return Fragment()
    }

}