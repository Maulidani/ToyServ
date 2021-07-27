package com.toyota.toyserv.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.toyota.toyserv.databinding.ActivityProfileCsBinding
import com.toyota.toyserv.databinding.ActivityProfileCustomerBinding

class ProfileActivity : AppCompatActivity() {
    lateinit var bindingCs: ActivityProfileCsBinding
    lateinit var bindingCustomer: ActivityProfileCustomerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingCs = ActivityProfileCsBinding.inflate(layoutInflater)
        bindingCustomer = ActivityProfileCustomerBinding.inflate(layoutInflater)

        val view = bindingCs.root
//        val view = bindingCustomer.root
        setContentView(view)


    }
}