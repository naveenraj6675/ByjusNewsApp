package com.android.byjusnewapp.models

import com.google.gson.annotations.SerializedName

class Response (

    @SerializedName("status")
    var status : String ?= null ,
    @SerializedName("totalResults")
    var totalResults :  Int ?= null ,
    @SerializedName("code")
    var code : String ?= null ,
    @SerializedName("message")
    var message : String ?= null ,
    @SerializedName("articles")
    var articlesList : ArrayList<Articles> ?= null


        )