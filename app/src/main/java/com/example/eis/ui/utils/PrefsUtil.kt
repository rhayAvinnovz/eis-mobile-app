package com.example.eis.ui.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.eis.ui.models.response.UserResponse
import com.google.gson.Gson

object PrefsUtil {

    private const val USER_KEY: String = "USER_KEY"

    private fun getPrefs(context : Context) : SharedPreferences {
        return context.getSharedPreferences(Constants.PREFS_KEY, 0)
    }

    fun setUser(context: Context, user: UserResponse) {
        getPrefs(context).edit().putString(USER_KEY, Gson().toJson(user)).apply()
    }

    fun getUser(context: Context): UserResponse? {
        val user = getPrefs(context).getString(USER_KEY, null)
        if (!user.isNullOrBlank()) {
            return Gson().fromJson(user, UserResponse::class.java)
        }
        return null
    }

    fun clear(context: Context) {
        getPrefs(context).edit().clear().apply()
    }
}