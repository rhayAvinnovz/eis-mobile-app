package com.example.eis.ui.models

import java.io.Serializable

data class Stationary(
    var id: Int?,
    var company: String?,
    var city: String?,
    var year: String?,
    var status: String?
) : Serializable