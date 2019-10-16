package com.fernandez.rico.jetpatck.ui.main

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.fernandez.rico.jetpatck.core.Failure
import com.fernandez.rico.jetpatck.core.ScreenState
import com.fernandez.rico.jetpatck.data.server.boundary.PhotoBoundaryCallback
import com.fernandez.rico.jetpatck.domain.Photo
import com.fernandez.rico.jetpatck.domain.paging.PhotoPaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class PhotoActivityViewModel: ViewModel(), KoinComponent
{

    private val job= Job()
    private val coroutineScope =  CoroutineScope(Dispatchers.IO + job)

    private val photoBoundaryCallback: PhotoBoundaryCallback by inject { parametersOf(coroutineScope) }

    private val photoPaging: PhotoPaging by inject { parametersOf(photoBoundaryCallback) }



    private val _mediatorLiveDataPhoto: MediatorLiveData<ScreenState<PhotoDataScreenState>> = MediatorLiveData()

    val photoScreenState: MediatorLiveData<ScreenState<PhotoDataScreenState>>
        get() = _mediatorLiveDataPhoto

    private var photoError: LiveData<Failure>
    private var photoResult: LiveData<PagedList<Photo>>


    init {
//        user = userPaging.getUsers()
        val data = photoPaging.getPhotos()

        photoResult = data.result
        photoError = data.error


        _mediatorLiveDataPhoto.addSource(photoResult,::handlePost)
        _mediatorLiveDataPhoto.addSource(photoError,::handlePostError)

    }

    private fun handlePost(list: PagedList<Photo>){
        _mediatorLiveDataPhoto.value=ScreenState.Render(PhotoDataScreenState.PhotoScreen(list))
    }

    private fun handlePostError(error: Failure){
        _mediatorLiveDataPhoto.value=ScreenState.Render(PhotoDataScreenState.PhotoDataError(error))
    }


    override fun onCleared() {
        job.cancel()

        super.onCleared()
    }
}