package com.example.eis.ui.models.request

import com.example.eis.ui.models.AreaModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SourceRequest (
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
    var vocValue: String? = ""
)   :   Serializable {
    constructor(area: AreaModel) : this() {
        this.generalId = area.generalId
        this.typeSource = area.typeSource
        this.otherSource = area.otherSource
        this.activityRate = area.activityRate
        this.durationMonths = area.durationMonths
        this.address = area.address
        this.utm = area.utm
        this.coValue = area.coValue
        this.noxValue = area.noxValue
        this.pmValue = area.pmValue
        this.soxValue = area.soxValue
        this.vocValue = area.vocValue
    }
}