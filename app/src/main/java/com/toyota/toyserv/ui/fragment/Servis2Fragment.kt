package com.toyota.toyserv.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.toyota.toyserv.databinding.FragmentServis2Binding

class Servis2Fragment(_type:String) : Fragment() {
    private val type = _type
    private var _binding: FragmentServis2Binding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServis2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (type=="ringan"){
            //ringan()
            binding.tvTest.text = type
        } else if (type=="berat"){
            //berat()
            binding.tvTest.text = type
        }
    }

}