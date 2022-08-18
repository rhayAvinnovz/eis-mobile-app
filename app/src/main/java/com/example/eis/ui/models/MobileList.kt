package com.example.eis.ui.models

import com.google.gson.annotations.SerializedName

class MobileList {
    @SerializedName("id")
    var generalId: String? = null
    var year: String? = null
    var region: String? = null
    var category: String? = null
    var province: String? = null
    var status_1: String? = null
    var status_2: String? = null
    var status_3: String? = null
    var status_4: String? = null
    @SerializedName("date_added")
    var dateAdded: String? = null
    var count: String? = null
}