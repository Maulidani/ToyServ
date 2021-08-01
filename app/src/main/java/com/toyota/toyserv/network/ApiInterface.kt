package com.test.belajarviewbinding

import com.toyota.toyserv.model.DataResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("vehicle_operation.php")
    fun vehicleOperation(): Call<DataResponse>

    @FormUrlEncoded
    @POST("service_request.php")
    fun requestServicePost(
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
    fun requestServiceGet(
        @Query("type") type: String,
        @Query("user") user: String
    ): Call<DataResponse>

    @GET("service.php")
    fun service(
        @Query("type") type: String,
        @Query("vehicle_operation") vehicleOperation: String
    ): Call<DataResponse>


}