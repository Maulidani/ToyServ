package com.toyota.toyserv.model

data class DataResponse(
    val data: ArrayList<DataResult>
)

data class DataResult(
    val id: Int,
    val title: String,
    val category: String,
    val author: String,
    val thumbnail: String,
    val path: String, val created_at: String
)
