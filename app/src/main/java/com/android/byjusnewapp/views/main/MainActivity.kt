package com.android.byjusnewapp.views.main

import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.byjusnewapp.R
import com.android.byjusnewapp.adapter.ListRecyclerAdapter
import com.android.byjusnewapp.databinding.ActivityMainBinding
import com.android.byjusnewapp.models.Articles
import com.android.byjusnewapp.viewmodel.ListViewModel
import com.android.byjusnewapp.views.base.MyAppCompatActivity

class MainActivity : MyAppCompatActivity() {

    private lateinit var mListAdapter : ListRecyclerAdapter
    private var mNewsList :  ArrayList<Articles> = ArrayList()
    private val listViewModel: ListViewModel by lazy {
        ViewModelProvider(this).get(ListViewModel::class.java)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpLoader(listViewModel)
        initViews()
    }

    override fun onErrorCalled(it: String?) {
        showSnackbar(it)
    }

    override fun initObservers() {
        listViewModel.listLiveData.observe(this, Observer {
            if (it != null && it.size > 0){
                if (mNewsList.size > 0)
                    mNewsList.clear()

                mNewsList.addAll(it)

                if (::mListAdapter.isInitialized){
                    mListAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun initViews(){
        listViewModel.getList("techcrunch",getString(R.string.api_key))

        mListAdapter = ListRecyclerAdapter(this,mNewsList)
        binding.listRV.layoutManager = LinearLayoutManager(this)
        binding.listRV.adapter = mListAdapter

    }
}