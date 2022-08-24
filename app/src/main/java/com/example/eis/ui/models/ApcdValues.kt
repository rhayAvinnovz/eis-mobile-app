package com.example.eis.ui.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApcdValues (
    @SerializedName("apcd")
    var apcd: String? = "",
    @SerializedName("other_apcd")
    var other_apcd: String? = "",
    @SerializedName("apsd_efficiency")
    var apsd_efficiency: String? = "",
)   :   Serializable