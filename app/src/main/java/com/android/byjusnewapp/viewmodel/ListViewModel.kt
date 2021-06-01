package com.android.byjusnewapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.android.byjusnewapp.models.Articles
import com.resmed.mynight.managers.utils.RetrofitManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : MyBaseViewModel(application) {

    val localArticleData = headlineRepository.articlesLiveData

    val listLiveData: MutableLiveData<ArrayList<Articles>> = MutableLiveData()

    fun getList(source: String, apiKey: String) {
        CoroutineScope(exceptionHandler).launch {
            val request = RetrofitManager.getInstance(getApplication()).getTechSearchApi()
                .getList(source, apiKey)
            val response = request.await()

            if (response.isSuccessful) {
                val apiResponse = response.body()!!
                if (apiResponse.response?.status == "ok") {
                    listLiveData.postValue(apiResponse.response?.articlesList)
                } else {
                    errorLiveData.postValue(apiResponse.response?.message)
                }
            }
        }
    }

    fun addArticles(data: Articles) {
        CoroutineScope(exceptionHandler).launch {
            headlineRepository.addItems(data)

        }
    }

    fun updateArticles(data: Articles){
        CoroutineScope(exceptionHandler).launch {
            headlineRepository.updateItem(data)
        }
    }

}