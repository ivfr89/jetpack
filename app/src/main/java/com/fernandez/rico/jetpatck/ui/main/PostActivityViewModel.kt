package com.fernandez.rico.jetpatck.ui.main

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.fernandez.rico.jetpatck.core.Failure
import com.fernandez.rico.jetpatck.core.ScreenState
import com.fernandez.rico.jetpatck.data.server.boundary.PostBoundaryCallback
import com.fernandez.rico.jetpatck.domain.Post
import com.fernandez.rico.jetpatck.domain.paging.PostPaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class PostActivityViewModel: ViewModel(), KoinComponent
{


    private val job= Job()
    private val coroutineScope =  CoroutineScope(Dispatchers.IO + job)

    private val postBoundaryCallback: PostBoundaryCallback by inject { parametersOf(coroutineScope) }

    private val postPaging: PostPaging by inject { parametersOf(postBoundaryCallback) }

    private val _mediatorLiveDataPost: MediatorLiveData<ScreenState<PostDataScreenState>> = MediatorLiveData()


    val postScreenState: MediatorLiveData<ScreenState<PostDataScreenState>>
        get() = _mediatorLiveDataPost

    private var postError: LiveData<Failure>
    private var postResult: LiveData<PagedList<Post>>


    init {
        val data = postPaging.getPosts()

        postResult = data.result
        postError = data.error


        _mediatorLiveDataPost.addSource(postResult,::handlePost)
        _mediatorLiveDataPost.addSource(postError,::handlePostError)

    }

    private fun handlePost(list: PagedList<Post>){
        _mediatorLiveDataPost.value=ScreenState.Render(PostDataScreenState.PostScreen(list))
    }

    private fun handlePostError(error: Failure){
        _mediatorLiveDataPost.value=ScreenState.Render(PostDataScreenState.PostDataError(error))
    }

    override fun onCleared() {
        job.cancel()

        _mediatorLiveDataPost.removeSource(postResult)
        _mediatorLiveDataPost.removeSource(postError)
        super.onCleared()
    }
}