package com.fernandez.rico.jetpatck.domain.repository

import com.fernandez.rico.jetpatck.core.BaseNetWorkRepository
import com.fernandez.rico.jetpatck.core.Either
import com.fernandez.rico.jetpatck.core.Failure
import com.fernandez.rico.jetpatck.data.db.AppDatabase
import com.fernandez.rico.jetpatck.data.server.ApiService
import com.fernandez.rico.jetpatck.data.server.NetworkHandler
import com.fernandez.rico.jetpatck.domain.Comment
import com.fernandez.rico.jetpatck.domain.Post


interface PostRepository {

    fun obtainPost(page: Int, limit: Int): Either<Failure,List<Post>>

    fun obtainCommentsFromPost(postId: Int, page: Int, limit: Int): Either<Failure,List<Comment>>



    class Network(val networkHandler: NetworkHandler,
                  val service: ApiService,
                  val db: AppDatabase
                  ) : BaseNetWorkRepository,
        PostRepository
    {
        override fun obtainPost(page: Int, limit: Int): Either<Failure, List<Post>> {

            return if(networkHandler.isConnected)
            {
                request(service.getPosts(page,limit),{
                    val data = it.map { postElement->postElement.toDomain() }
                    db.postDao().insert(data.map { dbItem-> dbItem.toDB() })
                    data

                }, emptyList())
            }else
                Either.Left(Failure.ConnectivityError)

        }


        override fun obtainCommentsFromPost(postId: Int, page: Int, limit: Int): Either<Failure, List<Comment>> {

            return if(networkHandler.isConnected)
            {
                request(service.getCommentsFromPost(postId,page,limit),{
                    it.map { it.toDomain() }
                }, emptyList())
            }else
                Either.Left(Failure.ConnectivityError)
        }

    }


}
