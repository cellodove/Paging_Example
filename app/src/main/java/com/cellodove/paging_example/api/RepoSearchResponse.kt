package com.cellodove.paging_example.api

import com.cellodove.paging_example.model.Repo
import com.google.gson.annotations.SerializedName

data class RepoSearchResponse(
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("items") val items: List<Repo> = emptyList(),
    val nextPage: Int? = null
)
