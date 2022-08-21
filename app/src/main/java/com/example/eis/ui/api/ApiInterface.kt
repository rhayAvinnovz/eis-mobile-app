package com.example.eis.ui.api

import com.example.eis.ui.models.AreaGeneral
import com.example.eis.ui.models.MobileGeneral
import com.example.eis.ui.models.request.AreaListRequest
import com.example.eis.ui.models.request.MobileListRequest
import com.example.eis.ui.models.request.SourceRequest
import com.example.eis.ui.models.request.VehicleRequest
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

//    ADDING GENERAL INFORMATION
    @POST("/avinnovz/denr-emb-r10/api/mobile/addgeneral")
    fun addMobileGeneral(@Body mobileGeneral: MobileGeneral): Observable<Int>

    @POST("/avinnovz/denr-emb-r10/api/area/addgeneral")
    fun addAreaGeneral(@Body areaGeneral: AreaGeneral): Observable<Int>

//    UPDATING GENERAL INFORMATION
    @POST("/avinnovz/denr-emb-r10/api/mobile/updategeneral")
    fun updateGeneralMobile(@Body mobileGeneral: MobileGeneral): Observable<Response<Any>>

    @POST("/avinnovz/denr-emb-r10/api/area/updategeneral")
    fun updateGeneralArea(@Body areaGeneral: AreaGeneral): Observable<Response<Any>>

//    DELETING VEHICLE ENTRY
    @POST("/avinnovz/denr-emb-r10/api/mobile/deleteVehicle")
    @FormUrlEncoded
    fun deleteVehicle(@Field("vehicle_id") vehicleId: Int): Observable<Response<Any>>

//    DELETING SOURCE ENTRY
    @POST("/avinnovz/denr-emb-r10/api/area/deletesource")
    @FormUrlEncoded
    fun deleteSource(@Field("source_id") sourceId: Int): Observable<Response<Any>>

//    ADDING VEHICLE ENTRY OF MOBILE
    @POST("/avinnovz/denr-emb-r10/api/mobile/addVehicle")
    fun addVehicle(@Body vehicleRequest: VehicleRequest): Observable<Response<Any>>

//    ADDING SOURCE ENTRY OF AREA
    @POST("/avinnovz/denr-emb-r10/api/area/addsource")
    fun addSource(@Body sourceRequest: SourceRequest): Observable<Response<Any>>

//    FETCHING GENERAL INFORMATION OF MOBILE ENTRY
    @POST("/avinnovz/denr-emb-r10/api/mobile/general")
    @FormUrlEncoded
    fun getMobileGeneral(@Field("general_id") generalId: Int): Observable<MobileGeneral>

//    FETCHING GENERAL INFORMATION OF AREA ENTRY
    @POST("/avinnovz/denr-emb-r10/api/area/general")
    @FormUrlEncoded
    fun getAreaGeneral(@Field("general_id") generalId: Int): Observable<AreaGeneral>


}