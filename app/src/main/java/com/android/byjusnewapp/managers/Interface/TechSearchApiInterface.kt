package com.android.byjusnewapp.managers.Interface

import com.android.byjusnewapp.models.response.ListApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TechSearchApiInterface {

    @GET("/top-headlines/?{sources}/&{apiKey}")
    fun getList(@Path("sources") sources : String,@Field("apiKey") apikey : String) : Deferred<Response<ListApiResponse>>

}