package com.fernandez.rico.jetpatck.ui.main

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.fernandez.rico.jetpatck.core.Failure
import com.fernandez.rico.jetpatck.core.ScreenState
import com.fernandez.rico.jetpatck.data.server.boundary.UserBoundaryCallback
import com.fernandez.rico.jetpatck.domain.User
import com.fernandez.rico.jetpatck.domain.paging.UserPaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class UserActivityViewModel: ViewModel(), KoinComponent
{
    private val job= Job()
    private val coroutineScope =  CoroutineScope(Dispatchers.IO + job)

    private val userBoundaryCallback: UserBoundaryCallback by inject { parametersOf(coroutineScope) }

    private val userPaging: UserPaging by inject { parametersOf(userBoundaryCallback) }


    private val _mediatorLiveDataUser: MediatorLiveData<ScreenState<UserDataScreenState>> = MediatorLiveData()


    val userScreenState: MediatorLiveData<ScreenState<UserDataScreenState>>
        get() = _mediatorLiveDataUser


    private var userError: LiveData<Failure>
    private var userResult: LiveData<PagedList<User>>


    init {
        val data = userPaging.getUsers()

        userResult = data.result
        userError = data.error


        _mediatorLiveDataUser.addSource(userResult,::handleUser)
        _mediatorLiveDataUser.addSource(userError,::handlePostError)

    }

    private fun handleUser(list: PagedList<User>){
        _mediatorLiveDataUser.value=ScreenState.Render(UserDataScreenState.UserScreen(list))
    }

    private fun handlePostError(error: Failure){
        _mediatorLiveDataUser.value=ScreenState.Render(UserDataScreenState.UserDataError(error))
    }

    override fun onCleared() {
        job.cancel()

        super.onCleared()
    }
}