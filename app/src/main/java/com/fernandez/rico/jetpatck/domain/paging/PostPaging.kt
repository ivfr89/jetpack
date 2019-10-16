package com.fernandez.rico.jetpatck.domain.paging

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.fernandez.rico.jetpatck.core.Failure
import com.fernandez.rico.jetpatck.core.extensions.empty
import com.fernandez.rico.jetpatck.data.db.AppDatabase
import com.fernandez.rico.jetpatck.data.server.boundary.PostBoundaryCallback
import com.fernandez.rico.jetpatck.domain.Post
import com.fernandez.rico.jetpatck.utils.Constants.PAGE_LIMIT


class PostPaging(val boundaryCallback: PostBoundaryCallback,
                 val db: AppDatabase
)
{
    class PostPagingResult(var error: LiveData<Failure>,
                           var result: LiveData<PagedList<Post>>)



    fun getPosts() : PostPagingResult
    {
        val dataSourceFactory = db.postDao().getPost().map { Post(it.user_id,it.id,it.title?:String.empty,it.body?:String.empty) }

        val data = LivePagedListBuilder(dataSourceFactory, PAGE_LIMIT)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return PostPagingResult(boundaryCallback.failure,data)
    }
}