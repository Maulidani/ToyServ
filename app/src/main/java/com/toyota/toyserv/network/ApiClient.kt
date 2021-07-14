package com.test.belajarviewbinding

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    const val URL = "https://app.kedai.or.id"

    //okhhtp

    val instances: ApiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiInterface::class.java)
    }

}