package com.example.eis.ui.models

import java.io.Serializable

data class Area (
    var id: String?,
    var year: String?,
    var province: String?,
    var city: String?,
    var status: String?,
    var count: String?
) : Serializable