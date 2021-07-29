package com.toyota.toyserv.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.toyota.toyserv.ui.fragment.Servis2Fragment
import com.toyota.toyserv.ui.fragment.ServisSaya2Fragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    itemNav: String,
    totalItem: Int,
    _idVehicleOperation: String
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val item = itemNav
    private val total = totalItem
    private val idVehicleOperation = _idVehicleOperation

    override fun getItemCount(): Int = total

    override fun createFragment(position: Int): Fragment {

        if (item == "servis") {
            return when (position) {
                0 -> Servis2Fragment("ringan", idVehicleOperation)
                1 -> Servis2Fragment("berat",idVehicleOperation)
                else -> Fragment()
            }
        }
        else if (item == "servis saya") {
            return when (position) {
                0 -> ServisSaya2Fragment("belum_dijadwalkan")
                1 -> ServisSaya2Fragment("sudah_dijadwalkan")
                2 -> ServisSaya2Fragment("selesai")
                else -> Fragment()
            }
        } else if (item == "Info") {

        }
        return Fragment()
    }

}