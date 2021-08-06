package com.test.belajarviewbinding

import com.toyota.toyserv.model.DataResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("type") type: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<DataResponse>

    @FormUrlEncoded
    @POST("register.php")
    fun register(
        @Field("name") fullName: String,
        @Field("vehicle") vehicle: String,
        @Field("police_number") policeNumber: String,
        @Field("phone") phoneNumber: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("type") type: String,
    ): Call<DataResponse>

    @GET("account.php")
    fun getAkun(
        @Query("type") type: String
    ): Call<DataResponse>

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