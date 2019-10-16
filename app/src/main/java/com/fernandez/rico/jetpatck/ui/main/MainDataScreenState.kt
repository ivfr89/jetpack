package com.fernandez.rico.jetpatck.ui.main

import androidx.paging.PagedList
import com.fernandez.rico.jetpatck.core.Failure
import com.fernandez.rico.jetpatck.domain.Photo
import com.fernandez.rico.jetpatck.domain.Post
import com.fernandez.rico.jetpatck.domain.User

sealed class PostDataScreenState
{
    class PostDataError(val error: Failure): PostDataScreenState()
    class PostScreen(val data: PagedList<Post>): PostDataScreenState()

}


sealed class UserDataScreenState
{
    class UserDataError(val error: Failure): UserDataScreenState()

    class UserScreen(val data: PagedList<User>) : UserDataScreenState()
}

sealed class PhotoDataScreenState {

    class PhotoDataError(val error: Failure) : PhotoDataScreenState()

    class PhotoScreen(val data: PagedList<Photo>) : PhotoDataScreenState()

}


