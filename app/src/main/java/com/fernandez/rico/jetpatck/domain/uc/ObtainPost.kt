package com.fernandez.rico.jetpatck.domain.uc

import com.fernandez.rico.jetpatck.core.Either
import com.fernandez.rico.jetpatck.core.Failure
import com.fernandez.rico.jetpatck.core.UseCase
import com.fernandez.rico.jetpatck.domain.Post
import com.fernandez.rico.jetpatck.domain.repository.PostRepository
import kotlinx.coroutines.CoroutineScope

class ObtainPost(private val repository: PostRepository) : UseCase<List<Post>, ObtainPost.Params,CoroutineScope>()
{
    override suspend fun run(params: Params): Either<Failure, List<Post>> {

        return repository.obtainPost(params.page, params.size)

    }


    class Params(val page: Int, val size: Int)
}