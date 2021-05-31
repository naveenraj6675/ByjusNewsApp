package com.android.byjusnewapp.models

import com.google.gson.annotations.SerializedName

class Articles (
    @SerializedName("source")
    var source: Source ?= null ,
    @SerializedName("author")
    var author : String ?= null ,
    @SerializedName("title")
    var title :  String ?= null ,
    @SerializedName("description")
    var desc :  String ?= null,
    @SerializedName("url")
    var url :  String ?= null ,
    @SerializedName("urlToImage")
    var imageUrl : String ?= null ,
    @SerializedName("publishedAt")
    var publishedAt :  String ?= null ,
    @SerializedName("content")
    var content : String ?= null

        )