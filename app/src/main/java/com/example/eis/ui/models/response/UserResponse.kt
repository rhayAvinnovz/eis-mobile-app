package com.example.eis.ui.models.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("user_id")
    var userId: String? = "",
    @SerializedName("username")
    var username: String? = "",
    @SerializedName("firstname")
    var firstname: String? = "",
    @SerializedName("lastname")
    var lastname: String? = "",
    @SerializedName("position")
    var position: String? = "",
    @SerializedName("department")
    var department: String? = ""
)