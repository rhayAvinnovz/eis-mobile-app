package com.example.eis.ui.models.request

import com.example.eis.ui.models.ApsiModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApsiRequest (
    @SerializedName("plant_id")
    var plantId: String? = "",
    @SerializedName("apsi_type")
    var apsiType: String? = "",
    @SerializedName("other_apsi_type")
    var otherApsi: String? = "",
    @SerializedName("apsi_capacity")
    var apsiCapacity: String? = "",
    @SerializedName("apsi_unit")
    var apsiUnit: String? = "",
    @SerializedName("apsi_size")
    var apsiSize: String? = "",
    @SerializedName("utm_easting")
    var utmEasting: String? = "",
    @SerializedName("utm_northing")
    var utmNorthing: String? = "",
    @SerializedName("fuel_type")
    var fuelType: String? = "",
    @SerializedName("other_fuel_type")
    var otherFuel: String? = "",
    @SerializedName("fuel_rate")
    var fuelRate: String? = "",
    @SerializedName("fuel_used")
    var fuelUsed: String? = "",
    @SerializedName("fuel_used_unit")
    var fuelUnit: String? = "",
    @SerializedName("operating_hours")
    var operatingHours: String? = "",
    @SerializedName("apcd")
    var apcd: String? = "",
    @SerializedName("apsd_efficiency")
    var apcdEfficiency: String? = "",
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
)   : Serializable {
    constructor(apsiModel: ApsiModel)   :   this(){
        this.plantId = apsiModel.plantId
        this.apsiType = apsiModel.apsiType
        this.otherApsi = apsiModel.otherApsi
        this.apsiCapacity = apsiModel.apsiCapacity
        this.apsiUnit = apsiModel.apsiUnit
        this.apsiSize = apsiModel.apsiSize
        this.utmEasting = apsiModel.utmEasting
        this.utmNorthing = apsiModel.utmNorthing
        this.fuelType = apsiModel.fuelType
        this.otherFuel = apsiModel.otherFuel
        this.fuelRate = apsiModel.fuelRate
        this.fuelUsed = apsiModel.fuelUsed
        this.fuelUnit = apsiModel.fuelUnit
        this.operatingHours = apsiModel.operatingHours
        this.apcd = apsiModel.apcd
        this.apcdEfficiency = apsiModel.apcdEfficiency
        this.coValue = apsiModel.coValue
        this.noxValue = apsiModel.noxValue
        this.pmValue = apsiModel.pmValue
        this.soxValue = apsiModel.soxValue
        this.vocValue = apsiModel.vocValue
    }
}