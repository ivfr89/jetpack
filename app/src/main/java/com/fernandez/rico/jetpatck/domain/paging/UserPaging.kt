package com.fernandez.rico.jetpatck.domain.paging

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.fernandez.rico.jetpatck.core.Failure
import com.fernandez.rico.jetpatck.data.db.AppDatabase
import com.fernandez.rico.jetpatck.data.server.boundary.UserBoundaryCallback
import com.fernandez.rico.jetpatck.domain.*
import com.fernandez.rico.jetpatck.utils.Constants.PAGE_LIMIT


class UserPaging(val boundaryCallback: UserBoundaryCallback,
                 val db: AppDatabase)
{
    class UserPagingResult(val error: LiveData<Failure>,
                           val result: LiveData<PagedList<User>>)



    fun getUsers() : UserPagingResult
    {
        val dataSourceFactory = db.userDao().getUsers().map { User(it.id,
            it.name,
            it.username,
            it.email,
            Address(it.address.street,it.address.suite,it.address.city,it.address.zipCode,
                Geolocation(it.address.geo.latitude,it.address.geo.longitude)
            ),
            it.phone,
            it.website,
            Company(it.company.name,it.company.catchPhrase,it.company.bs)
        ) }



        val data = LivePagedListBuilder(dataSourceFactory, PAGE_LIMIT)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return UserPagingResult(boundaryCallback.failure,data)
    }
}