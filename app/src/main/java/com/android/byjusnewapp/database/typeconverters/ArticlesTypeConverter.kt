package com.android.byjusnewapp.database.typeconverters

import android.provider.MediaStore
import androidx.room.TypeConverter
import com.android.byjusnewapp.helpers.JsonUtils
import com.android.byjusnewapp.models.Articles
import com.android.byjusnewapp.models.Source

class ArticlesTypeConverter {

    @TypeConverter
    fun fromArticle(value: Articles): String{
        return JsonUtils.toJson(value)
    }

    @TypeConverter
    fun toArticle(value: String): Articles{
        return JsonUtils.parseJson(value)
    }

    @TypeConverter
    fun fromSource(value : Source) : String{
        return  JsonUtils.toJson(value)
    }


    @TypeConverter
    fun toSource(value : String) : Source {
        return JsonUtils.parseJson(value)
    }
}