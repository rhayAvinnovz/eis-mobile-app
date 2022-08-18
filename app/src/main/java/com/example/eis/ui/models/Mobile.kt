package com.example.eis.ui.models

import java.io.Serializable

data class Mobile (
    var id: String?,
    var category: String?,
    var year: String?,
    var province: String?,
    var status: String?,
    var count: String?
) : Serializable