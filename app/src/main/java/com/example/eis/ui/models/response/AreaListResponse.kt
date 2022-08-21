package com.example.eis.ui.models.response

import com.example.eis.ui.models.AreaList
import com.google.gson.annotations.SerializedName

class AreaListResponse {
    var page: Int? = null
    @SerializedName("per_page")
    var perPage: Int? = null
    var total: Int? = null
    var lists: ArrayList<AreaList> = ArrayList()
}