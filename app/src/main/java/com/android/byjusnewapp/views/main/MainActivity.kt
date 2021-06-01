package com.android.byjusnewapp.views.main

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.byjusnewapp.R
import com.android.byjusnewapp.adapter.ListRecyclerAdapter
import com.android.byjusnewapp.constants.ExtraIntents
import com.android.byjusnewapp.databinding.ActivityMainBinding
import com.android.byjusnewapp.helpers.JsonUtils
import com.android.byjusnewapp.models.Articles
import com.android.byjusnewapp.viewmodel.ListViewModel
import com.android.byjusnewapp.views.base.MyAppCompatActivity

class MainActivity : MyAppCompatActivity() {

    private lateinit var mListAdapter : ListRecyclerAdapter
    private var mNewsList :  ArrayList<Articles> = ArrayList()
    private var mLocalList : ArrayList<Articles> = ArrayList()
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

                if (mNewsList.size > 0){
                    mNewsList.forEach { serverList ->
                        if (mLocalList.isEmpty()){
                            listViewModel.addArticles(serverList)
                        }else{
                            mLocalList.forEach {  localList ->
                                if (serverList.title == localList.title){
                                    listViewModel.updateArticles(serverList)
                                }else{
                                    listViewModel.addArticles(serverList)
                                }
                            }
                        }
                    }
                }
                if (::mListAdapter.isInitialized){
                    mListAdapter.notifyDataSetChanged()
                }
            }
        })


        listViewModel.localArticleData.observe(this, Observer {
                if (it.isNotEmpty() ){
                    mLocalList.addAll(it)
                    if (!isNetworkAvailable()){
                        if (mNewsList.size > 0)
                            mNewsList.clear()

                        mNewsList.addAll(it)
                        mListAdapter.notifyDataSetChanged()

                    }else{
                        it.forEach {

                        }
                    }
                }else{
                    if (!isNetworkAvailable()){
                        showConfirmation("Cancel","trun on","No Internet connection","Please turn on your internet to continue",DialogInterface.OnClickListener { dialogInterface, i ->
                            getList()
                        })
                    }
                }
        })
    }

    private fun initViews(){

        if (isNetworkAvailable()){
           getList()
        }

        mListAdapter = ListRecyclerAdapter(this,mNewsList)
        binding.listRV.layoutManager = LinearLayoutManager(this)
        binding.listRV.adapter = mListAdapter

        mListAdapter.onItemClicked = {
            val intent = Intent(this,DetailActivity::class.java)
            intent.putExtra(ExtraIntents.ARTICLE_ITEM,JsonUtils.toJson(it))
            startActivity(intent)
        }

    }
    
    private fun getList(){
        listViewModel.getList("techcrunch",getString(R.string.api_key))
    }
}