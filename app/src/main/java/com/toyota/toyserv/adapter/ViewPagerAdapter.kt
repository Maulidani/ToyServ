package com.toyota.toyserv.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.toyota.toyserv.ui.fragment.Akun2Fragment
import com.toyota.toyserv.ui.fragment.PermintaanServis2Fragment
import com.toyota.toyserv.ui.fragment.Servis2Fragment
import com.toyota.toyserv.ui.fragment.ServisSaya2Fragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    itemNav: String,
    totalItem: Int,
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
        } else if (item == "servis saya") {
            return when (position) {
                0 -> ServisSaya2Fragment("belum_dijadwalkan")
                1 -> ServisSaya2Fragment("sudah_dijadwalkan")
                2 -> ServisSaya2Fragment("selesai")
                else -> Fragment()
            }
        } else if (item == "permintaan_servis") {
            return when (position) {
                0 -> ServisSaya2Fragment("belum_dijadwalkan")
                1 -> ServisSaya2Fragment("sudah_dijadwalkan")
                2 -> ServisSaya2Fragment("selesai")
                else -> Fragment()
            }
        } else if (item == "permintaan_servis_all") {
            return when (position) {
                0 -> PermintaanServis2Fragment("belum_dijadwalkan")
                1 -> PermintaanServis2Fragment("sudah_dijadwalkan")
                2 -> PermintaanServis2Fragment("selesai")
                else -> Fragment()
            }
        } else if (item == "akun") {
            return when (position) {
                0 -> Akun2Fragment("customer")
                1 -> Akun2Fragment("customer_service")
                else -> Fragment()
            }
        }
        return Fragment()
    }

}