package com.fernandez.rico.jetpatck.domain.paging

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.fernandez.rico.jetpatck.core.Failure
import com.fernandez.rico.jetpatck.core.extensions.empty
import com.fernandez.rico.jetpatck.data.db.AppDatabase
import com.fernandez.rico.jetpatck.data.server.boundary.PhotoBoundaryCallback
import com.fernandez.rico.jetpatck.domain.*
import com.fernandez.rico.jetpatck.utils.Constants.PAGE_LIMIT


class PhotoPaging(val boundaryCallback: PhotoBoundaryCallback,
                  val db: AppDatabase)
{
    class PhotoPagingResult(val error: LiveData<Failure>,
                           val result: LiveData<PagedList<Photo>>)



    fun getPhotos() : PhotoPagingResult
    {
        val dataSourceFactory = db.photoDao().getPhotos().map { Photo(it.albumId,
            it.id,
            it.title ?: String.empty,
            it.url ?: String.empty,
            it.thumbnailUrl ?: String.empty
        ) }



        val data = LivePagedListBuilder(dataSourceFactory, PAGE_LIMIT)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return PhotoPagingResult(boundaryCallback.failure,data)
    }
}