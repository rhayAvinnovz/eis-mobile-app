package com.example.eis.ui.models.request

import com.example.eis.ui.models.ApsiModel
import com.example.eis.ui.models.PlantModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlantRequest (
    @SerializedName("general_id")
    var generalId: String? = "",
    @SerializedName("plant_name")
    var plantName: String? = "",
    @SerializedName("plant_address")
    var plantAddress: String? = "",
    var apsi:ArrayList<ApsiModel> = ArrayList()
)   :   Serializable {
    constructor(plantModel: PlantModel) : this() {
        this.generalId = plantModel.generalId
        this.plantName = plantModel.plantName
        this.plantAddress = plantModel.plantAddress
        this.apsi = plantModel.apsi
    }
}