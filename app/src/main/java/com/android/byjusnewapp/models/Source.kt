package com.android.byjusnewapp.models

import com.google.gson.annotations.SerializedName

class Source (
    @SerializedName("id")
    var id :  String  ?= null,
    @SerializedName("name")
    var name : String ?= null
)