package com.example.eis.ui.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AreaGeneral (

    @SerializedName("id")
    var generalId: String? = "",
    @SerializedName("year")
    var year: String? = "",
    @SerializedName("province")
    var province: String? = "",
    @SerializedName("city")
    var city: String? = "",
    @SerializedName("user_id")
    var userId: String? = "",
    @SerializedName("user_name")
    var userName: String? = "",
    var areas:ArrayList<AreaModel> = ArrayList()

) : Serializable