package com.fernandez.rico.jetpatck.di

import com.fernandez.rico.jetpatck.data.db.AppDatabase
import com.fernandez.rico.jetpatck.data.server.ApiService
import com.fernandez.rico.jetpatck.data.server.HeaderInterceptor
import com.fernandez.rico.jetpatck.data.server.NetworkHandler
import com.fernandez.rico.jetpatck.data.server.boundary.PhotoBoundaryCallback
import com.fernandez.rico.jetpatck.data.server.boundary.PostBoundaryCallback
import com.fernandez.rico.jetpatck.data.server.boundary.UserBoundaryCallback
import com.fernandez.rico.jetpatck.domain.paging.PhotoPaging
import com.fernandez.rico.jetpatck.domain.paging.PostPaging
import com.fernandez.rico.jetpatck.domain.paging.UserPaging
import com.fernandez.rico.jetpatck.domain.repository.PhotoRepository
import com.fernandez.rico.jetpatck.domain.repository.PostRepository
import com.fernandez.rico.jetpatck.domain.repository.UserRepository
import com.fernandez.rico.jetpatck.domain.uc.ObtainPost
import com.fernandez.rico.jetpatck.ui.main.PhotoActivityViewModel
import com.fernandez.rico.jetpatck.ui.main.PostActivityViewModel
import com.fernandez.rico.jetpatck.ui.main.UserActivityViewModel
import com.fernandez.rico.jetpatck.utils.Constants
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val serviceModule = module {


    single {
        OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS).build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(Constants.END_POINT_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }

    single {
        NetworkHandler(get())
    }

}



val database = module {

    single {
        AppDatabase.getInstance(get())
    }
}


val useCaseModule = module {


    factory {
        ObtainPost(get())
    }

}

val paging = module {

    factory {
        (boundaryCallback: PostBoundaryCallback) -> PostPaging(boundaryCallback,get())
    }

    factory {
            (boundaryCallback: UserBoundaryCallback) -> UserPaging(boundaryCallback,get())
    }

    factory {
            (boundaryCallback: PhotoBoundaryCallback) -> PhotoPaging(boundaryCallback,get())
    }
    factory {
        (scope: CoroutineScope) ->
        PostBoundaryCallback(get(), scope)
    }

    factory {
            (scope: CoroutineScope) ->
        UserBoundaryCallback(get(), scope)
    }

    factory {
            (scope: CoroutineScope) ->
        PhotoBoundaryCallback(get(), scope)
    }
}


val repositoryModule = module {

    single<PostRepository> {
        PostRepository.Network(get(),get(),get())
    }

    single<UserRepository>{
        UserRepository.Network(get(),get(),get())
    }

    single<PhotoRepository>{
        PhotoRepository.Network(get(),get(),get())
    }

}

val viewModelModule = module {

    viewModel { PostActivityViewModel() }
    viewModel { PhotoActivityViewModel() }
    viewModel { UserActivityViewModel() }
}