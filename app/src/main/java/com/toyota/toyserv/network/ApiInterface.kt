package com.test.belajarviewbinding

import com.toyota.toyserv.model.DataResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("vehicle_operation.php")
    fun vehicleOperation(): Call<DataResponse>

    @FormUrlEncoded
    @POST("service_request.php")
    fun requestService(
        @Field("id") id: String,

        @Field("name") name: String,
        @Field("user") user: String,
        @Field("note") note: String,

        @Field("cs") cs: String,
        @Field("service_at") serviceAt: String,

        @Field("finish_at") finishAt: String,
        @Field("next_at") nextAt: String
    ): Call<DataResponse>

    @GET("service_request.php")
    fun requestService(
        @Query("type") type: String
    ): Call<DataResponse>

    @GET("service.php")
    fun service(): Call<DataResponse>


}