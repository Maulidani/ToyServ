package com.toyota.toyserv.model

data class DataResponse(
    val value: String,
    val result: ArrayList<DataResult>
)

data class DataResult(
    val id: String,
    val name: String
)
