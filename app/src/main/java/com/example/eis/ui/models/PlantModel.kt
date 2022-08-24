package com.example.eis.ui.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlantModel (

    @SerializedName("id")
    var plantId: String? = "",
    @SerializedName("general_id")
    var generalId: String? = "",
    @SerializedName("plant_name")
    var plantName: String? = "",
    @SerializedName("plant_address")
    var plantAddress: String? = "",
    var apsi:ArrayList<ApsiModel> = ArrayList()

)   :   Serializable