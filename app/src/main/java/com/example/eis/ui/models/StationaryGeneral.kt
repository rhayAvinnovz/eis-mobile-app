package com.example.eis.ui.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StationaryGeneral (
    @SerializedName("id")
    var generalId: String? = "",
    @SerializedName("year")
    var year: String? = "",
    @SerializedName("province")
    var province: String? = "",
    @SerializedName("city")
    var city: String? = "",
    @SerializedName("company")
    var company: String? = "",
    @SerializedName("mother_company")
    var mother_company: String? = "",
    @SerializedName("address")
    var address: String? = "",
    @SerializedName("industry_type")
    var industry_type: String? = "",
    @SerializedName("other_industry_type")
    var other_industry_type: String? = "",
    @SerializedName("user_id")
    var userId: String? = "",
    @SerializedName("user_name")
    var userName: String? = "",
    var plants:ArrayList<PlantModel> = ArrayList()
)   :   Serializable