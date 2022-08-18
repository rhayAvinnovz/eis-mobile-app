package com.example.eis.ui.models.request

import com.example.eis.ui.models.Vehicles
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VehicleRequest(
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
    var vocValue: String? = ""
) : Serializable {

    constructor(vehicles: Vehicles) : this() {
        this.generalId = vehicles.generalId
        this.vehicleType = vehicles.vehicleType
        this.noVehicle = vehicles.noVehicle
        this.fuelType = vehicles.fuelType
        this.vktValue = vehicles.vktValue
        this.percentSulfur = vehicles.percentSulfur
        this.tripValue = vehicles.tripValue
        this.coValue = vehicles.coValue
        this.noxValue = vehicles.noxValue
        this.pmValue = vehicles.pmValue
        this.soxValue = vehicles.soxValue
        this.vocValue = vehicles.vocValue
    }
}