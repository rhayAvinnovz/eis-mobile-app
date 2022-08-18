package com.example.eis.ui.base

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.eis.R
import com.example.eis.ui.api.ApiAction
import com.example.eis.ui.api.ApiRequest
import com.example.eis.ui.api.OnApiRequestListener
import com.example.eis.ui.models.AreaGeneral
import com.example.eis.ui.models.Mobile
import com.example.eis.ui.models.MobileGeneral
import com.example.eis.ui.utils.Constants

open class BaseActivity: AppCompatActivity(), OnApiRequestListener {

    protected lateinit var apiRequest: ApiRequest
    protected var generalInformation: MobileGeneral = MobileGeneral()
    protected var generalInformationArea: AreaGeneral = AreaGeneral()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiRequest = ApiRequest(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        if (intent.hasExtra(Constants.GENERAL_INFORMATION_MOBILE))
            generalInformation =
                intent.getSerializableExtra(Constants.GENERAL_INFORMATION_MOBILE) as MobileGeneral
        if (intent.hasExtra(Constants.GENERAL_INFORMATION_AREA))
            generalInformationArea =
                intent.getSerializableExtra(Constants.GENERAL_INFORMATION_AREA) as AreaGeneral
    }

    fun animateToRight() {
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left)
    }

    fun animateToLeft() {
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right)
    }

    fun showMessage(message: String?) {
        Toast.makeText(this, message ?: "Something went wrong.", Toast.LENGTH_SHORT).show()
    }

    override fun onApiRequestStart(apiAction: ApiAction) {
    }

    override fun onApiRequestFailed(apiAction: ApiAction, t: Throwable) {
    }

    override fun onApiRequestSuccess(apiAction: ApiAction, result: Any) {
    }

    fun getGeneralInfo(): MobileGeneral {
        return generalInformation
    }
    fun getGeneralInfoArea(): AreaGeneral {
        return generalInformationArea
    }
    fun Activity.hideKeyboard() = WindowInsetsControllerCompat(window, window.decorView).hide(
        WindowInsetsCompat.Type.ime())
}