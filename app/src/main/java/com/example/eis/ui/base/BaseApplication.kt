package com.example.eis.ui.base

import androidx.multidex.MultiDexApplication
import com.example.eis.BuildConfig
import com.example.eis.ui.utils.RetrofitUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

class BaseApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initRetrofit()
    }

    private fun initRetrofit() {
        var cacheDir : File? = externalCacheDir
        val connectionTimeout = 30
        val readTimeout = 30

        if (cacheDir == null) {
            cacheDir = getCacheDir()
        }
        val cache = Cache(cacheDir!!, 10 * 1024 * 1024)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor { chain ->
                val request = chain.request()
                chain.proceed(request)
            }
            .cache(cache)
            .connectTimeout(connectionTimeout.toLong(), TimeUnit.SECONDS)
            .readTimeout(readTimeout.toLong(), TimeUnit.SECONDS)
            .build()

        RetrofitUtil.getInstance().init(BuildConfig.BASE_URL, okHttpClient)

        //PicassoUtil.getInstance().initPicasso(this, okHttpClient)
    }
}