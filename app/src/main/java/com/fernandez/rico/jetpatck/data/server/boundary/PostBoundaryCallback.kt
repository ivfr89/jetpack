package com.fernandez.rico.jetpatck.data.server.boundary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.fernandez.rico.jetpatck.core.BoundaryCoroutine
import com.fernandez.rico.jetpatck.core.Either
import com.fernandez.rico.jetpatck.core.Failure
import com.fernandez.rico.jetpatck.domain.Post
import com.fernandez.rico.jetpatck.domain.repository.PostRepository
import com.fernandez.rico.jetpatck.utils.Constants.PAGE_LIMIT
import kotlinx.coroutines.*

class PostBoundaryCallback(private val repository: PostRepository,
                           private val coroutineScope: CoroutineScope)
    : PagedList.BoundaryCallback<Post>(), BoundaryCoroutine<CoroutineScope> {

    private var lastRequestedPage = 0
    private val _failure = MutableLiveData<Failure>()

    val failure : LiveData<Failure>
        get() = _failure



    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()

        execute(coroutineScope){

            repository.obtainPost(0,PAGE_LIMIT).also {
                when(it){
                    is Either.Right -> lastRequestedPage=1
                    is Either.Left -> _failure.postValue(it.a)

                }
            }
        }


    }

    override fun onItemAtEndLoaded(itemAtEnd: Post) {
        super.onItemAtEndLoaded(itemAtEnd)


        execute(coroutineScope){
            repository.obtainPost(lastRequestedPage,PAGE_LIMIT).also {
                when(it){
                    is Either.Right -> lastRequestedPage++
                    is Either.Left -> _failure.postValue(it.a)
                }
            }
        }

    }

}