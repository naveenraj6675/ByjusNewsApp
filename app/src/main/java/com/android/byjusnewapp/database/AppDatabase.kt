package com.android.byjusnewapp.database

import android.content.Context
import androidx.room.*
import com.android.byjusnewapp.models.Articles
import com.android.byjusnewapp.database.typeconverters.ArticlesTypeConverter

@Database(entities = arrayOf(Articles::class), version = 1)
@TypeConverters(ArticlesTypeConverter::class)
abstract  class AppDatabase : RoomDatabase() {

    abstract fun artcileDao(): ArticleDao


    companion object{
        private var INSTANCE: AppDatabase? = null
        private val DB_NAME = "byjus.db"
        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, DB_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(object : RoomDatabase.Callback() {
                            }).build()
                    }
                }
            }
            return INSTANCE
        }

        fun deleteDatabase() {
            synchronized(AppDatabase::class.java) {
                INSTANCE?.clearAllTables()
            }
        }
    }

}