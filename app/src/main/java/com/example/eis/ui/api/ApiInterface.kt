package com.example.eis.ui.api

import com.example.eis.ui.models.AreaGeneral
import com.example.eis.ui.models.MobileGeneral
import com.example.eis.ui.models.StationaryGeneral
import com.example.eis.ui.models.request.*
import com.example.eis.ui.models.response.*
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface  ApiInterface {

//    INITIATING LOGIN
    @POST("/avinnovz/denr-emb-r10/api/user/login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<UserResponse>

//    FETCHING LIST
    @POST("/avinnovz/denr-emb-r10/api/mobile/lists")
    fun getMobileList(@Body mobileListRequest: MobileListRequest):
            Observable<MobileListResponse>

    @POST("/avinnovz/denr-emb-r10/api/area/lists")
    fun getAreaList(@Body areaListRequest: AreaListRequest):
            Observable<AreaListResponse>

    @POST("/avinnovz/denr-emb-r10/api/stationary/lists")
    fun getStationaryList(@Body stationaryListRequest: StationaryListRequest):
            Observable<StationaryListResponse>

//    ADDING GENERAL INFORMATION
    @POST("/avinnovz/denr-emb-r10/api/mobile/addgeneral")
    fun addMobileGeneral(@Body mobileGeneral: MobileGeneral): Observable<Int>

    @POST("/avinnovz/denr-emb-r10/api/area/addgeneral")
    fun addAreaGeneral(@Body areaGeneral: AreaGeneral): Observable<Int>

    @POST("/avinnovz/denr-emb-r10/api/stationary/addgeneral")
    fun addStationaryGeneral(@Body stationaryGeneral: StationaryGeneral): Observable<Int>

//    UPDATING GENERAL INFORMATION
    @POST("/avinnovz/denr-emb-r10/api/mobile/updategeneral")
    fun updateGeneralMobile(@Body mobileGeneral: MobileGeneral): Observable<Response<Any>>

    @POST("/avinnovz/denr-emb-r10/api/area/updategeneral")
    fun updateGeneralArea(@Body areaGeneral: AreaGeneral): Observable<Response<Any>>

    @POST("/avinnovz/denr-emb-r10/api/stationary/updategeneral")
    fun updateGeneralStationary(@Body stationaryGeneral: StationaryGeneral): Observable<Response<Any>>

//    DELETING VEHICLE ENTRY
    @POST("/avinnovz/denr-emb-r10/api/mobile/deleteVehicle")
    @FormUrlEncoded
    fun deleteVehicle(@Field("vehicle_id") vehicleId: Int): Observable<Response<Any>>

//    DELETING SOURCE ENTRY
    @POST("/avinnovz/denr-emb-r10/api/area/deletesource")
    @FormUrlEncoded
    fun deleteSource(@Field("source_id") sourceId: Int): Observable<Response<Any>>

//    DELETING PLANT ENTRY
    @POST("/avinnovz/denr-emb-r10/api/stationary/deleteplant")
    @FormUrlEncoded
    fun deletePlant(@Field("plant_id") plantId: Int): Observable<Response<Any>>

//    DELETING APSI ENTRY
    @POST("/avinnovz/denr-emb-r10/api/stationary/deleteapsi")
    @FormUrlEncoded
    fun deleteApsi(@Field("plant_id") plantId: Int): Observable<Response<Any>>

//    ADDING VEHICLE ENTRY OF MOBILE
    @POST("/avinnovz/denr-emb-r10/api/mobile/addVehicle")
    fun addVehicle(@Body vehicleRequest: VehicleRequest): Observable<Response<Any>>

//    ADDING SOURCE ENTRY OF AREA
    @POST("/avinnovz/denr-emb-r10/api/area/addsource")
    fun addSource(@Body sourceRequest: SourceRequest): Observable<Response<Any>>

//    ADDING PLANT
    @POST("/avinnovz/denr-emb-r10/api/stationary/addplant")
    fun addPlant(@Body plantRequest: PlantRequest): Observable<Int>

//    ADDING APSI
    @POST("/avinnovz/denr-emb-r10/api/stationary/addapsi")
    fun addApsi(@Body apsiRequest: ApsiRequest): Observable<Int>

//    FETCHING GENERAL INFORMATION OF MOBILE ENTRY
    @POST("/avinnovz/denr-emb-r10/api/mobile/general")
    @FormUrlEncoded
    fun getMobileGeneral(@Field("general_id") generalId: Int): Observable<MobileGeneral>

//    FETCHING GENERAL INFORMATION OF AREA ENTRY
    @POST("/avinnovz/denr-emb-r10/api/area/general")
    @FormUrlEncoded
    fun getAreaGeneral(@Field("general_id") generalId: Int): Observable<AreaGeneral>

//    FETCHING GENERAL INFORMATION OF AREA ENTRY
    @POST("/avinnovz/denr-emb-r10/api/stationary/general")
    @FormUrlEncoded
    fun getStationaryGeneral(@Field("general_id") generalId: Int): Observable<StationaryGeneral>


}