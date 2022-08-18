package com.example.eis.ui.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MobileGeneral (

    @SerializedName("id")
    var generalId: String? = "",
    @SerializedName("year")
    var year: String? = "",
    @SerializedName("category")
    var category: String? = "",
    @SerializedName("province")
    var province: String? = "",
    @SerializedName("road_thorough_fare")
    var rt_fare: String? = "",
    @SerializedName("utm_northing")
    var utmNorthing: String? = "",
    @SerializedName("utm_easting")
    var utmEasting: String? = "",
    @SerializedName("user_id")
    var userId: String? = "",
    @SerializedName("user_name")
    var userName: String? = "",
    var vehicles:ArrayList<Vehicles> = ArrayList()

) : Serializable