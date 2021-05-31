package com.resmed.mynight.managers.utils

import android.content.Context
import android.content.ContextWrapper
import com.android.byjusnewapp.R
import com.android.byjusnewapp.managers.Interface.TechSearchApiInterface
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitManager(context: Context) : ContextWrapper(context) {

    companion object {
        private val api_key: String = "2Ik7JCwjBD91wMeOydJyj9xAn6tyKP3m5oVH3wqM"
        private var INSTANCE: RetrofitManager? = null
        private lateinit var defaultRetrofit: Retrofit
        private var userRetrofit: Retrofit? = null

        fun getInstance(context: Context): RetrofitManager {
            if (INSTANCE == null) {
                initManager(context)
            }
            return INSTANCE!!
        }

        private fun initManager(context: Context) {
            INSTANCE = RetrofitManager(context)

            val interceptor = Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Accept", "*/*")
                    .header("Content-Type" ,"application/json")
                    .header("x-api-key", api_key)
                    .build()

                chain.proceed(request)
            }

            val httpClient: OkHttpClient.Builder =
                OkHttpClient.Builder().addInterceptor(interceptor)
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(200, TimeUnit.SECONDS);


                val logInterceptor = HttpLoggingInterceptor()
                logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                httpClient.addInterceptor(logInterceptor)


            val gson = GsonBuilder()

                .create()

            defaultRetrofit = Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))// + context.getString(R.string.url_version))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(httpClient.build())
                .build()

        }
    }

    fun getTechSearchApi() : TechSearchApiInterface {
        return defaultRetrofit.create(TechSearchApiInterface::class.java)
    }
}