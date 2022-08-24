package com.example.eis.ui.api

import android.annotation.SuppressLint
import com.example.eis.ui.models.*
import com.example.eis.ui.models.request.*
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

    fun getAreaList(areaListRequest: AreaListRequest) {
        onApiRequestListener.onApiRequestStart(ApiAction.GET_AREA_LIST)
        handleObservableResult(ApiAction.GET_AREA_LIST, apiInterface!!.getAreaList(areaListRequest))
    }

    fun getStationaryList(stationaryListRequest: StationaryListRequest) {
        onApiRequestListener.onApiRequestStart(ApiAction.GET_STATIONARY_LIST)
        handleObservableResult(ApiAction.GET_STATIONARY_LIST, apiInterface!!.getStationaryList(stationaryListRequest))
    }

    fun getMobileGeneral(generalId: Int) {
        onApiRequestListener.onApiRequestStart(ApiAction.GET_MOBILE_GENERAL)
        handleObservableResult(ApiAction.GET_MOBILE_GENERAL, apiInterface!!.getMobileGeneral(generalId))
    }

    fun getAreaGeneral(generalId: Int) {
        onApiRequestListener.onApiRequestStart(ApiAction.GET_AREA_GENERAL)
        handleObservableResult(ApiAction.GET_AREA_GENERAL, apiInterface!!.getAreaGeneral(generalId))
    }

    fun getStationaryGeneral(generalId: Int) {
        onApiRequestListener.onApiRequestStart(ApiAction.GET_STATIONARY_GENERAL)
        handleObservableResult(ApiAction.GET_STATIONARY_GENERAL, apiInterface!!.getStationaryGeneral(generalId))
    }

    fun addMobileGeneral(mobileGeneral: MobileGeneral) {
        onApiRequestListener.onApiRequestStart(ApiAction.ADD_MOBILE_GENERAL)
        handleObservableResult(ApiAction.ADD_MOBILE_GENERAL, apiInterface!!.addMobileGeneral(mobileGeneral))
    }

    fun addAreaGeneral(areaGeneral: AreaGeneral) {
        onApiRequestListener.onApiRequestStart(ApiAction.ADD_AREA_GENERAL)
        handleObservableResult(ApiAction.ADD_AREA_GENERAL, apiInterface!!.addAreaGeneral(areaGeneral))
    }

    fun addStationaryGeneral(stationaryGeneral: StationaryGeneral) {
        onApiRequestListener.onApiRequestStart(ApiAction.ADD_STATIONARY_GENERAL)
        handleObservableResult(ApiAction.ADD_STATIONARY_GENERAL, apiInterface!!.addStationaryGeneral(stationaryGeneral))
    }

    fun updateMobileGeneral(mobileGeneral: MobileGeneral) {
        onApiRequestListener.onApiRequestStart(ApiAction.UPDATE_MOBILE_GENERAL)
        handleObservableResult(ApiAction.UPDATE_MOBILE_GENERAL, apiInterface!!.updateGeneralMobile(mobileGeneral))
    }

    fun updateAreaGeneral(areaGeneral: AreaGeneral) {
        onApiRequestListener.onApiRequestStart(ApiAction.UPDATE_AREA_GENERAL)
        handleObservableResult(ApiAction.UPDATE_AREA_GENERAL, apiInterface!!.updateGeneralArea(areaGeneral))
    }

    fun updateStationaryGeneral(stationaryGeneral: StationaryGeneral) {
        onApiRequestListener.onApiRequestStart(ApiAction.UPDATE_STATIONARY_GENERAL)
        handleObservableResult(ApiAction.UPDATE_STATIONARY_GENERAL, apiInterface!!.updateGeneralStationary(stationaryGeneral))
    }

    fun deleteVehicle(vehicleId: Int) {
        onApiRequestListener.onApiRequestStart(ApiAction.DELETE_MOBILE_VEHICLE)
        handleObservableResult(ApiAction.DELETE_MOBILE_VEHICLE, apiInterface!!.deleteVehicle(vehicleId))
    }

    fun deleteSource(sourceId: Int) {
        onApiRequestListener.onApiRequestStart(ApiAction.DELETE_AREA_SOURCE)
        handleObservableResult(ApiAction.DELETE_AREA_SOURCE, apiInterface!!.deleteSource(sourceId))
    }

    fun deletePlant(plantId: Int) {
        onApiRequestListener.onApiRequestStart(ApiAction.DELETE_STATIONARY_PLANT)
        handleObservableResult(ApiAction.DELETE_STATIONARY_PLANT, apiInterface!!.deletePlant(plantId))
    }

    fun deleteApsi(plantId: Int) {
        onApiRequestListener.onApiRequestStart(ApiAction.DELETE_PLANT_APSI)
        handleObservableResult(ApiAction.DELETE_PLANT_APSI, apiInterface!!.deleteApsi(plantId))
    }

    fun addVehicle(vehicle: VehicleRequest) {
        onApiRequestListener.onApiRequestStart(ApiAction.ADD_MOBILE_VEHICLE)
        handleObservableResult(ApiAction.ADD_MOBILE_VEHICLE, apiInterface!!.addVehicle(vehicle))
    }

    fun addSource(source: SourceRequest) {
        onApiRequestListener.onApiRequestStart(ApiAction.ADD_AREA_SOURCE)
        handleObservableResult(ApiAction.ADD_AREA_SOURCE, apiInterface!!.addSource(source))
    }

    fun addPlant(plantRequest: PlantRequest) {
        onApiRequestListener.onApiRequestStart(ApiAction.ADD_STATIONARY_PLANT)
        handleObservableResult(ApiAction.ADD_STATIONARY_PLANT, apiInterface!!.addPlant(plantRequest))
    }

    fun addApsi(apsiRequest: ApsiRequest) {
        onApiRequestListener.onApiRequestStart(ApiAction.ADD_PLANT_APSI)
        handleObservableResult(ApiAction.ADD_PLANT_APSI, apiInterface!!.addApsi(apsiRequest))
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