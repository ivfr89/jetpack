package com.fernandez.rico.jetpatck.domain.repository

import com.fernandez.rico.jetpatck.core.BaseNetWorkRepository
import com.fernandez.rico.jetpatck.core.Either
import com.fernandez.rico.jetpatck.core.Failure
import com.fernandez.rico.jetpatck.data.db.AppDatabase
import com.fernandez.rico.jetpatck.data.server.ApiService
import com.fernandez.rico.jetpatck.data.server.NetworkHandler
import com.fernandez.rico.jetpatck.domain.Photo

interface PhotoRepository {

    fun obtainPhotos(page: Int, limit: Int): Either<Failure, List<Photo>>


    class Network(val networkHandler: NetworkHandler,
                  val service: ApiService,
                  val db: AppDatabase
    ) : BaseNetWorkRepository,
        PhotoRepository
    {
        override fun obtainPhotos(page: Int, limit: Int): Either<Failure, List<Photo>> {

            return if(networkHandler.isConnected)
            {
                request(service.getPhotos(page,limit),{
                    val data = it.map { userElement->userElement.toDomain() }
                    db.photoDao().insert(data.map { dbItem-> dbItem.toDB() })
                    data

                }, emptyList())
            }else
                Either.Left(Failure.ConnectivityError)
        }

    }

}