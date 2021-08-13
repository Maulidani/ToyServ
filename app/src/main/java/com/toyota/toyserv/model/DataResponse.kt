package com.toyota.toyserv.model

data class DataResponse(
    val value: String,
    val message: String,
    val name: String,
    val type: String,
    val id: String,
    val result: ArrayList<DataResult>
)

data class DataResult(
    val id: String,
    val id_user: String,
    val id_service: String,
    val month: String,
    val name: String,
    val service_name: String,
    val username: String,
    val password: String,
    val user_name: String,
    val cs_name: String,
    val vehicle: String,
    val phone: String,
    val description: String,
    val police_number: String,
    val user: String,
    val created_at: String,
    val note: String,
    val cs: String,
    val service_at: String,
    val finish_at: String,
    val next_at: String,
    val type_service: String,
    val type: String,
    var expendable: Boolean = false,
    val customer: String,
    val title: String,
    val date: String,
    val body: String


)