package com.fernandez.rico.jetpatck.data.db.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fernandez.rico.jetpatck.data.db.UserDB
import com.fernandez.rico.jetpatck.utils.Constants

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<UserDB>)

    /*@Query("SELECT * FROM ${Constants.TABLE_NAMES.TB_POST} WHERE (title LIKE :queryString) OR (body LIKE " +
            ":queryString)")
    fun getPostByTitle(queryString: String): DataSource.Factory<Int,Post>*/

    @Query("SELECT * FROM ${Constants.TABLE_NAMES.TB_USER}")
    fun getUsers(): DataSource.Factory<Int,UserDB>
}
