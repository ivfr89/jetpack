package com.fernandez.rico.jetpatck.data.db.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fernandez.rico.jetpatck.data.db.PostDB
import com.fernandez.rico.jetpatck.utils.Constants

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<PostDB>)


    @Query("SELECT * FROM ${Constants.TABLE_NAMES.TB_POST}")
    fun getPost(): DataSource.Factory<Int,PostDB>
}
