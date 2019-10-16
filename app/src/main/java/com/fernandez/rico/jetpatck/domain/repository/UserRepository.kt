package com.fernandez.rico.jetpatck.domain.repository

import com.fernandez.rico.jetpatck.core.BaseNetWorkRepository
import com.fernandez.rico.jetpatck.core.Either
import com.fernandez.rico.jetpatck.core.Failure
import com.fernandez.rico.jetpatck.data.db.AppDatabase
import com.fernandez.rico.jetpatck.data.server.ApiService
import com.fernandez.rico.jetpatck.data.server.NetworkHandler
import com.fernandez.rico.jetpatck.domain.User


interface UserRepository {

    fun obtainUsers(page: Int, limit: Int): Either<Failure, List<User>>


    class Network(val networkHandler: NetworkHandler,
                  val service: ApiService,
                  val db: AppDatabase
                  ) : BaseNetWorkRepository,
        UserRepository
    {
        override fun obtainUsers(page: Int, limit: Int): Either<Failure, List<User>> {

            return if(networkHandler.isConnected)
            {
                request(service.getUsers(page,limit),{
                    val data = it.map { userElement->userElement.toDomain() }
                    db.userDao().insert(data.map { dbItem-> dbItem.toDB() })
                    data

                }, emptyList())
            }else
                Either.Left(Failure.ConnectivityError)


        }

    }


}
