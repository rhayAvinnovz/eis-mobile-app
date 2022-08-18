package com.example.eis.ui.api

import com.example.eis.ui.models.MobileGeneral
import com.example.eis.ui.models.request.MobileListRequest
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

//    FETCHING LIST OF MOBILE VEHICLE ENTRY
    @POST("/avinnovz/denr-emb-r10/api/mobile/lists")
    fun getMobileList(@Body mobileListRequest: MobileListRequest):
            Observable<MobileListResponse>

//    ADDING GENERAL INFORMATION OF MOBILE ENTRY
    @POST("/avinnovz/denr-emb-r10/api/mobile/addgeneral")
    fun addMobileGeneral(@Body mobileGeneral: MobileGeneral): Observable<Int>

//    ADDING GENERAL INFORMATION OF MOBILE ENTRY
    @POST("/avinnovz/denr-emb-r10/api/mobile/updategeneral")
    fun updateGeneralMobile(@Body mobileGeneral: MobileGeneral): Observable<Response<Any>>

//    DELETING VEHICLE ENTRY
    @POST("/avinnovz/denr-emb-r10/api/mobile/deleteVehicle")
    @FormUrlEncoded
    fun deleteVehicle(@Field("vehicle_id") vehicleId: Int): Observable<Response<Any>>

//    ADDING VEHICLE ENTRY OF MOBILE
    @POST("/avinnovz/denr-emb-r10/api/mobile/addVehicle")
    fun addVehicle(@Body vehicleRequest: VehicleRequest): Observable<Response<Any>>

//    FETCHING GENERAL INFORMATION OF MOBILE ENTRY
    @POST("/avinnovz/denr-emb-r10/api/mobile/general")
    @FormUrlEncoded
    fun getMobileGeneral(@Field("general_id") generalId: Int): Observable<MobileGeneral>


}