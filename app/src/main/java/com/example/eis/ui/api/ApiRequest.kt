package com.example.eis.ui.api

import android.annotation.SuppressLint
import com.example.eis.ui.models.*
import com.example.eis.ui.models.request.MobileListRequest
import com.example.eis.ui.models.request.SourceRequest
import com.example.eis.ui.models.request.VehicleRequest
import com.example.eis.ui.utils.RetrofitUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class ApiRequest(private val onApiRequestListener: OnApiRequestListener) {

    private var apiInterface: ApiInterface? = RetrofitUtil.getInstance().getApiInterface()

    fun login(username: String, password: String) {
        onApiRequestListener.onApiRequestStart(ApiAction.LOGIN)
        handleObservableResult(ApiAction.LOGIN, apiInterface!!.login(username, password))
    }

    fun getMobileList(mobileListRequest: MobileListRequest) {
        onApiRequestListener.onApiRequestStart(ApiAction.GET_MOBILE_LIST)
        handleObservableResult(ApiAction.GET_MOBILE_LIST, apiInterface!!.getMobileList(mobileListRequest))
    }

    fun getMobileGeneral(generalId: Int) {
        onApiRequestListener.onApiRequestStart(ApiAction.GET_MOBILE_GENERAL)
        handleObservableResult(ApiAction.GET_MOBILE_GENERAL, apiInterface!!.getMobileGeneral(generalId))
    }

    fun addMobileGeneral(mobileGeneral: MobileGeneral) {
        onApiRequestListener.onApiRequestStart(ApiAction.ADD_MOBILE_GENERAL)
        handleObservableResult(ApiAction.ADD_MOBILE_GENERAL, apiInterface!!.addMobileGeneral(mobileGeneral))
    }

    fun addAreaGeneral(areaGeneral: AreaGeneral) {
        onApiRequestListener.onApiRequestStart(ApiAction.ADD_AREA_GENERAL)
        handleObservableResult(ApiAction.ADD_AREA_GENERAL, apiInterface!!.addAreaGeneral(areaGeneral))
    }

    fun updateMobileGeneral(mobileGeneral: MobileGeneral) {
        onApiRequestListener.onApiRequestStart(ApiAction.UPDATE_MOBILE_GENERAL)
        handleObservableResult(ApiAction.UPDATE_MOBILE_GENERAL, apiInterface!!.updateGeneralMobile(mobileGeneral))
    }

    fun deleteVehicle(vehicleId: Int) {
        onApiRequestListener.onApiRequestStart(ApiAction.DELETE_MOBILE_VEHICLE)
        handleObservableResult(ApiAction.DELETE_MOBILE_VEHICLE, apiInterface!!.deleteVehicle(vehicleId))
    }

    fun addVehicle(vehicle: VehicleRequest) {
        onApiRequestListener.onApiRequestStart(ApiAction.ADD_MOBILE_VEHICLE)
        handleObservableResult(ApiAction.ADD_MOBILE_VEHICLE, apiInterface!!.addVehicle(vehicle))
    }

    fun addSource(source: SourceRequest) {
        onApiRequestListener.onApiRequestStart(ApiAction.ADD_AREA_SOURCE)
        handleObservableResult(ApiAction.ADD_AREA_SOURCE, apiInterface!!.addSource(source))
    }

    @SuppressLint("CheckResult")
    private fun handleObservableResult(apiAction: ApiAction, observable: Observable<*>) {
        observable.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result -> onApiRequestListener.onApiRequestSuccess(apiAction, result)},
                { throwable ->
                    onApiRequestListener.onApiRequestFailed(apiAction, throwable as Throwable)
                })
    }
}