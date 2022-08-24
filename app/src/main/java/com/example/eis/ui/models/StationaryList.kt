package com.example.eis.ui.models

import com.google.gson.annotations.SerializedName

class StationaryList {

    @SerializedName("id")
    var generalId: String? = null
    var year: String? = null
    var region: String? = null
    var province: String? = null
    var city: String? = null
    var company: String? = null
    var mother_company: String? = null
    var address: String? = null
    var industry_type: String? = null
    var other_industry_type: String? = null
    var status_1: String? = null
    var status_2: String? = null
    var status_3: String? = null
    var status_4: String? = null
    var added_by: String? = null
    @SerializedName("date_added")
    var dateAdded: String? = null
    var count: String? = null
}