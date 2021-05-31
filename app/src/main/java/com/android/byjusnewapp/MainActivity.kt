package com.android.byjusnewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.byjusnewapp.views.base.MyAppCompatActivity

class MainActivity : MyAppCompatActivity() {


    private val listViewModel: ListViewModel by lazy {
        ViewModelProvider(this).get(ListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpLoader(listViewModel)
        initViews()
    }

    override fun onErrorCalled(it: String?) {
        showSnackbar(it)
    }

    override fun initObservers() {
        listViewModel.listLiveData.observe(this, Observer {
            if (it != null && it.size > 0){

            }
        })
    }

    private fun initViews(){
        listViewModel.getList("techcrunch",getString(R.string.api_key))

    }
}