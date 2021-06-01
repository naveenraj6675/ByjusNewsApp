package com.android.byjusnewapp.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.android.byjusnewapp.database.AppDatabase
import com.android.byjusnewapp.database.ArticleDao
import com.android.byjusnewapp.models.Articles


class HeadlineRepository(val application: Application) {

    private val articlesDao : ArticleDao by lazy {
        AppDatabase.getDatabase(application)!!.artcileDao()
    }


    companion object {
        private val TAG = "HeadlineRepository"
        private var INSTANCE: HeadlineRepository? = null
        fun getInstance(application: Application): HeadlineRepository {
            return if (INSTANCE != null) {
                INSTANCE!!
            } else {
                INSTANCE = HeadlineRepository(application)
                INSTANCE!!
            }
        }
        fun clearInstance() {
            INSTANCE = null
        }
    }

    val articlesLiveData = articlesDao.getAll()


    val errorLiveData = MutableLiveData<String>()



    suspend fun addItems(list : Articles){
        articlesDao.insert(list)

    }

    suspend fun updateItem(list : Articles){
        articlesDao.update(list)

    }

}