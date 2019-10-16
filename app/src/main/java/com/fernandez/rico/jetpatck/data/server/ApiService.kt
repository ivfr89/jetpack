package com.fernandez.rico.jetpatck.data.server

import com.fernandez.rico.jetpatck.utils.Constants
import retrofit2.Call
import retrofit2.http.Query

interface ApiService {


    companion object Factory
    {
        const val POST_ENDPOINT = "posts/"
        const val COMMENTS_ENDPOINT = "comments/"
        const val USERS_ENDPOINT = "users/"
        const val PHOTOS_ENDPOINT = "photos/"

        const val PARAM_START = "_start"
        const val PARAM_LIMIT = "_limit"
        const val PARAM_POST_ID = "postId"


        const val POST_DATA   = ""

    }

    @retrofit2.http.GET(POST_ENDPOINT) fun getPosts(@Query(PARAM_START) page: Int= Constants.DEFAULT_PAGE,
                                                    @Query(PARAM_LIMIT) limit: Int = Constants.PAGE_LIMIT
                                                    ): Call<List<PostEntity>>

    @retrofit2.http.GET(COMMENTS_ENDPOINT) fun getComments(@Query(PARAM_START) page: Int= Constants.DEFAULT_PAGE,
                                                           @Query(PARAM_LIMIT) limit: Int = Constants.PAGE_LIMIT): Call<List<CommentEntity>>

    @retrofit2.http.GET(COMMENTS_ENDPOINT) fun getCommentsFromPost(@Query(PARAM_POST_ID) postId: Int, @Query(PARAM_START) page: Int= Constants.DEFAULT_PAGE,
                                                           @Query(PARAM_LIMIT) limit: Int = Constants.PAGE_LIMIT): Call<List<CommentEntity>>


    @retrofit2.http.GET(PHOTOS_ENDPOINT) fun getPhotos(@Query(PARAM_START) page: Int= Constants.DEFAULT_PAGE,
                                                       @Query(PARAM_LIMIT) limit: Int = Constants.PAGE_LIMIT): Call<List<PhotoEntity>>

    @retrofit2.http.GET(USERS_ENDPOINT) fun getUsers(@Query(PARAM_START) page: Int= Constants.DEFAULT_PAGE,
                                                     @Query(PARAM_LIMIT) limit: Int = Constants.PAGE_LIMIT): Call<List<UserEntity>>

}