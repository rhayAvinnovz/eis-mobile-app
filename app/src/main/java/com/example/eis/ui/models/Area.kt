package com.example.eis.ui.models

import java.io.Serializable

data class Area (
    var id: Int?,
    var source: String?,
    var year: String?,
    var city: String?,
    var status: String?
) : Serializable