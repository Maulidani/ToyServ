package com.toyota.toyserv.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.belajarviewbinding.ApiClient
import com.toyota.toyserv.R
import com.toyota.toyserv.adapter.RvAdapter
import com.toyota.toyserv.databinding.FragmentServiceRinganBinding
import com.toyota.toyserv.model.DataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceRinganFragment : Fragment() {
    private var _binding: FragmentServiceRinganBinding? = null
    private val binding get() = _binding!!
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: RvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentServiceRinganBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.rv.layoutManager = layoutManager

        val page = 1
        showdata(page)
    }

    private fun showdata(page: Int) {
        ApiClient.instances.getDataEbook(page).enqueue(object : Callback<DataResponse> {
            override fun onResponse(
                call: Call<DataResponse>,
                response: Response<DataResponse>
            ) {
                if (isAdded) {
                    if (response.isSuccessful) {
                        adapter = RvAdapter(response.body()!!.data)
                        binding.rv.adapter = adapter
                        adapter.notifyDataSetChanged()

                    } else {
                        //error
                    }
                } else {
                    if (isAdded) {
                        //error
                    }
                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                //error
            }
        })
    }

}