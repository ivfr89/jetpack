package com.fernandez.rico.jetpatck.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fernandez.rico.jetpatck.data.db.dao.PhotoDao
import com.fernandez.rico.jetpatck.data.db.dao.PostDao
import com.fernandez.rico.jetpatck.data.db.dao.UserDao
import com.fernandez.rico.jetpatck.utils.Constants


@Database(entities = [PostDB::class,UserDB::class, PhotoDB::class],version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase()
{
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
    abstract fun photoDao(): PhotoDao


    companion object{

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase
        {
            synchronized(this)
            {
                return INSTANCE ?: buildDatabase(context)
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, Constants.DB_NAME)
                .build()
    }


}