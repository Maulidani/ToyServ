package com.test.belajarviewbinding

import com.toyota.toyserv.model.DataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/api/ebooks")
    fun getDataEbook(
        @Query("page") page: Int
    ): Call<DataResponse>

}