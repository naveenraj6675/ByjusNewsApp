package com.android.byjusnewapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.android.byjusnewapp.enums.LoaderStatus
import com.android.byjusnewapp.repositories.HeadlineRepository
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.json.JSONObject
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

open class MyBaseViewModel(application: Application) : AndroidViewModel(application),
    CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + rootJob

    protected val TAG: String = this.javaClass.simpleName

    protected var errorLiveData = MutableLiveData<String?>()

    // ...because this is what we'll want to expose
    val errorMediatorLiveData = MediatorLiveData<String?>()

    var isLoading = MutableLiveData<LoaderStatus>()

    val rootJob = Job()

    protected val handler = CoroutineExceptionHandler { _, exception ->
        isLoading.postValue(LoaderStatus.failed)
        errorLiveData.postValue(exception.message)
        exception.printStackTrace()
    }

    val headlineRepository : HeadlineRepository by lazy {
        HeadlineRepository.getInstance(application)
    }

    init {
        errorMediatorLiveData.addSource(errorLiveData) { result: String? ->
            result?.let {
                errorMediatorLiveData.value = result
            }
        }
    }


    protected val exceptionHandler: CoroutineContext =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            isLoading.postValue(LoaderStatus.failed)
            errorLiveData.postValue(throwable.message)
            throwable.printStackTrace()
        }

}