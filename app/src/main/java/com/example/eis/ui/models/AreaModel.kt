package com.example.eis.ui.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AreaModel (

    @SerializedName("id")
    var vehicleId: String? = "",
    @SerializedName("general_id")
    var generalId: String? = "",
    @SerializedName("type_area_source")
    var typeSource: String? = "",
    @SerializedName("other_type_area_source")
    var otherSource: String? = "",
    @SerializedName("activity_rate")
    var activityRate: String? = "",
    @SerializedName("duration_in_monhts")
    var durationMonths: String? = "",
    @SerializedName("address")
    var address: String? = "",
    @SerializedName("utm")
    var utm: String? = "",
    @SerializedName("co")
    var coValue: String? = "",
    @SerializedName("nox")
    var noxValue: String? = "",
    @SerializedName("pm")
    var pmValue: String? = "",
    @SerializedName("sox")
    var soxValue: String? = "",
    @SerializedName("voc")
    var vocValue: String? = "",
    @SerializedName("total")
    var totalValue: String? = "",

    ) : Serializable