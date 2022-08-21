package com.example.eis.ui.models.request

import com.google.gson.annotations.SerializedName

class AreaListRequest(page: Int, count: Int) {
    var page: Int? = page
    @SerializedName("per_page")
    var perPage: Int? = count
}