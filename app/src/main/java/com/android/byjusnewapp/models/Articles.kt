package com.android.byjusnewapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Articles (
    @PrimaryKey(autoGenerate = true)
    var priId:Int  = 0,
    @SerializedName("source")
    var source: Source ?= null,
    @SerializedName("author")
    var author : String,
    @SerializedName("title")
    var title :  String ,
    @SerializedName("description")
    var desc :  String,
    @SerializedName("url")
    var url :  String  ,
    @SerializedName("urlToImage")
    var imageUrl : String ,
    @SerializedName("publishedAt")
    var publishedAt :  String,
    @SerializedName("content")
    var content : String

        )