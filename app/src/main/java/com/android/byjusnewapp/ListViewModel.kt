package com.android.byjusnewapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.byjusnewapp.models.Articles
import com.android.byjusnewapp.viewmodel.MyBaseViewModel
import com.resmed.mynight.managers.utils.RetrofitManager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListViewModel(application: Application) : MyBaseViewModel(application) {

    val listLiveData: MutableLiveData<ArrayList<Articles>> = MutableLiveData()

    fun getList(source : String , apiKey : String){
        CoroutineScope(exceptionHandler).launch {
            val request = RetrofitManager.getInstance(getApplication()).getTechSearchApi().getList(source, apiKey)
            val response = request.await()

            if (response.isSuccessful){
                val apiResponse = response.body()!!

                if (apiResponse.response?.status == "ok"){
                    listLiveData.postValue(apiResponse.response?.articlesList)
                }else{
                    errorLiveData.postValue(apiResponse.response?.message)
                }

            }
        }
    }


}