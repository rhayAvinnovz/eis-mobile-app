package com.example.eis.ui.models.response

import com.example.eis.ui.models.MobileList
import com.google.gson.annotations.SerializedName

class MobileListResponse {
    var page: Int? = null
    @SerializedName("per_page")
    var perPage: Int? = null
    var total: Int? = null
    var lists: ArrayList<MobileList> = ArrayList()
}