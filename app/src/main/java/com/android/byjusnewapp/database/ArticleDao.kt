package com.android.byjusnewapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.byjusnewapp.models.Articles

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles")
    fun getAll(): LiveData<List<Articles>>

    @Insert
    fun insert(article : Articles): Long

    @Update
    fun update(article: Articles)

    @Delete
    fun delete(article: Articles)

    @Query("SELECT COUNT(priId) FROM articles")
    fun getCount(): Int

    @Query("DELETE FROM Articles")
    fun nukeTable()
}