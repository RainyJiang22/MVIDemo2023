package com.example.core.network

import android.app.Service
import okhttp3.Interceptor
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author jiangshiyu
 * @date 2023/12/3
 */
abstract class BaseRetrofitClient {

    companion object {
        private const val TIME_OUT = 30L
    }


    private val client: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
            .addInterceptor(getHttpLoggingInterceptor())
            .addInterceptor(getMyInterceptor())
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        handleBuilder(builder)
        builder.build()
    }


    abstract fun handleBuilder(builder: OkHttpClient.Builder)

    //自定义拦截
    abstract fun getMyInterceptor(): Interceptor

    //日志拦截
    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
//        if (BuildConfig.DEBUG) {
//            logging.level = HttpLoggingInterceptor.Level.BODY
//        } else {
//            logging.level = HttpLoggingInterceptor.Level.BASIC
//        }

        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }


    fun <S> getService(serviceClass: Class<S>, baseUrl: String): S {
        return Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(serviceClass)
    }

}