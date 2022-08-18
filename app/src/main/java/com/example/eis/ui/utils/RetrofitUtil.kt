package com.example.eis.ui.utils

import com.example.eis.ui.api.ApiInterface
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtil {

    private val retrofitUtil = RetrofitUtil
    private var retrofit: Retrofit? = null
    private var apiInterface: ApiInterface? = null

    fun init(baseUrl: String, okHttpClient: OkHttpClient) {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        apiInterface = retrofit?.create(ApiInterface::class.java)
    }

    fun getInstance(): RetrofitUtil {
        return retrofitUtil
    }

    fun getApiInterface(): ApiInterface? {
        return apiInterface
    }
}