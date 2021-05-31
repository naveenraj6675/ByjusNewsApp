package com.android.byjusnewapp.models.response

import com.android.byjusnewapp.models.Response
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ListApiResponse {

    var response : Response? = null

    class ListDeserializer  : JsonDeserializer<ListApiResponse> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): ListApiResponse {

            val trainerAPi = ListApiResponse()
            val jsonObject = json!!.asJsonObject


            trainerAPi.response = Gson().fromJson(jsonObject, Response::class.java)

            return trainerAPi
        }
    }

}