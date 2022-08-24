package com.example.eis.ui.models

import java.io.Serializable

data class Stationary(
    var id: String?,
    var company: String?,
    var province: String?,
    var city: String?,
    var year: String?,
    var status: String?,
    var count: String?
) : Serializable