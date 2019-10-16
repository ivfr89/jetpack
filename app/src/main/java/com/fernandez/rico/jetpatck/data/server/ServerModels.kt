package com.fernandez.rico.jetpatck.data.server

import com.fernandez.rico.jetpatck.core.extensions.default
import com.fernandez.rico.jetpatck.core.extensions.empty
import com.fernandez.rico.jetpatck.domain.*


abstract class ModelEntity<Domain>
{
    abstract fun toDomain() : Domain

    companion object
    {
        inline fun <reified T> create(f: ()->T): T = f()
    }
}

data class PostEntity(val userId: Int, val id: Int, val title: String, val body: String): ModelEntity<Post>()
{
    override fun toDomain(): Post = Post(userId,id,title,body)

    companion object{
        fun empty() : PostEntity = create { PostEntity(Int.default,Int.default,String.empty,String.empty) }
    }

}

data class CommentEntity(val postId: Int, val id: Int, val name: String, val email: String,val body: String) : ModelEntity<Comment>(){

    override fun toDomain(): Comment = Comment(postId,id,name,email,body)

    companion object{
        fun empty() : CommentEntity = create { CommentEntity(Int.default,Int.default,String.empty,String.empty,String.empty) }
    }

}

data class UserEntity(val id: Int,
                      val name: String,
                      val username: String,
                      val email: String,
                      val address: AddressEntity,
                      val phone: String,
                      val website: String,
                      val company: CompanyEntity
                      ): ModelEntity<User>()
{
    override fun toDomain(): User = User(id,name,username,email,address.toDomain(),phone,website,company.toDomain())

    companion object{

        fun empty() : UserEntity = create { UserEntity(Int.default,String.empty,String.empty,
            String.empty,AddressEntity.empty(),String.empty,String.empty, CompanyEntity.empty()) }

    }

}

data class PhotoEntity(val albumId: Int,
                 val id: Int,
                 val title: String,
                 val url: String,
                 val thumbnailUrl: String
                 ): ModelEntity<Photo>()
{
    override fun toDomain(): Photo = Photo(albumId,id,title,url,thumbnailUrl)

    companion object
    {
        fun empty() : PhotoEntity = create { PhotoEntity(Int.default,Int.default,String.empty,String.empty,String.empty) }
    }

}

data class AddressEntity(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: GeolocationEntity
): ModelEntity<Address>()
{
    override fun toDomain(): Address = Address(street,suite,city,zipcode,geo.toDomain())

    companion object{
        fun empty() : AddressEntity = create { AddressEntity(String.empty,String.empty,String.empty,String.empty,GeolocationEntity.empty()) }
    }
}

data class CompanyEntity(val name: String, val catchPhrase: String, val bs: String): ModelEntity<Company>()
{
    override fun toDomain(): Company = Company(name, catchPhrase, bs)

    companion object {
        fun empty() : CompanyEntity = create { CompanyEntity(String.empty,String.empty,String.empty) }
    }
}

data class GeolocationEntity(val lat: String, val lng: String): ModelEntity<Geolocation>()
{
    override fun toDomain(): Geolocation = Geolocation(lat,lng)

    companion object{
        fun empty() : GeolocationEntity = create { GeolocationEntity(String.empty,String.empty) }
    }
}

