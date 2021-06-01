package com.android.byjusnewapp.views.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import com.android.byjusnewapp.constants.ExtraIntents
import com.android.byjusnewapp.databinding.ActivityDetailBinding
import com.android.byjusnewapp.helpers.DateHelper
import com.android.byjusnewapp.helpers.JsonUtils
import com.android.byjusnewapp.models.Articles

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var mArticles: Articles

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val articleString = intent.getStringExtra(ExtraIntents.ARTICLE_ITEM)
        if (articleString != null ){
            mArticles = JsonUtils.parseJson(articleString)
        }

        initViews()

    }

    private fun initViews(){

        if (::mArticles.isInitialized){
            binding.backgroundIV.load(mArticles.imageUrl)
            binding.descriptionTV.text = mArticles.desc
            binding.authorNameTV.text = mArticles.author
            binding.publishDateTV.text = DateHelper.getPublisherDateFromAPi(mArticles.publishedAt)
            binding.titleTv.text = mArticles.title
        }

        binding.backIV.setOnClickListener {
            onBackPressed()
        }

    }


}