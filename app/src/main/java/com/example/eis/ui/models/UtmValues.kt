package com.example.eis.ui.models

import com.google.gson.annotations.SerializedName

data class UtmValues (
    @SerializedName("utm_northing")
    var northingInput: String? = "",
    @SerializedName("utm_easting")
    var eastingInput: String? = ""
)