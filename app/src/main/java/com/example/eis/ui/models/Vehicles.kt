package com.example.eis.ui.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Vehicles (

    @SerializedName("id")
    var vehicleId: String? = "",
    @SerializedName("general_id")
    var generalId: String? = "",
    @SerializedName("vehicle_type")
    var vehicleType: String? = "",
    @SerializedName("no_vehicle")
    var noVehicle: String? = "",
    @SerializedName("fuel_type")
    var fuelType: String? = "",
    @SerializedName("vkt")
    var vktValue: String? = "",
    @SerializedName("percent_sulfur")
    var percentSulfur: String? = "",
    @SerializedName("trip")
    var tripValue: String? = "",
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