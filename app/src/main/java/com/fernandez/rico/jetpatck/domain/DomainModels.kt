package com.fernandez.rico.jetpatck.domain

import android.os.Parcel
import android.os.Parcelable
import com.fernandez.rico.jetpatck.core.extensions.empty
import com.fernandez.rico.jetpatck.data.db.*


abstract class ModelDomain<Database>
{
    abstract fun toDB() : Database
}

data class Post(val userId: Int, val id: Int, val title: String, val body: String) : Parcelable, ModelDomain<PostDB>() {

    override fun toDB(): PostDB = PostDB(id,userId,title,body)

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: String.empty,
        parcel.readString() ?: String.empty
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(userId)
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(body)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}

data class Comment(val postId: Int, val id: Int, val name: String, val email: String,val body: String) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: String.empty,
        parcel.readString() ?: String.empty,
        parcel.readString()?: String.empty
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(postId)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(body)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Comment> {
        override fun createFromParcel(parcel: Parcel): Comment {
            return Comment(parcel)
        }

        override fun newArray(size: Int): Array<Comment?> {
            return arrayOfNulls(size)
        }
    }
}

data class User(
    val id: Int,
    val name: String,
    val userName: String,
    val email: String,
    val address: Address,
    val phone: String,
    val website: String,
    val company: Company
) : ModelDomain<UserDB>(), Parcelable{
    override fun toDB(): UserDB =
        UserDB(id, name, userName, email, address.toDB(), phone, website, company.toDB())

    constructor(source: Parcel) : this(
    source.readInt(),
    source.readString() ?: String.empty,
    source.readString() ?: String.empty,
    source.readString() ?: String.empty,
    source.readParcelable<Address>(Address::class.java.classLoader) ?: Address(String.empty,String.empty,String.empty,String.empty,Geolocation(String.empty,String.empty)),
    source.readString() ?: String.empty,
    source.readString() ?: String.empty,
    source.readParcelable<Company>(Company::class.java.classLoader) ?: Company(String.empty,String.empty,String.empty)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(name)
        writeString(userName)
        writeString(email)
        writeParcelable(address, 0)
        writeString(phone)
        writeString(website)
        writeParcelable(company, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}

data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipCode: String,
    val geo: Geolocation
) : Parcelable, ModelDomain<AddressEmbed>() {

    override fun toDB(): AddressEmbed  = AddressEmbed(street,suite,city,zipCode,geo.toDB())


    constructor(source: Parcel) : this(
        source.readString() ?: "",
        source.readString() ?: String.empty,
        source.readString() ?: String.empty,
        source.readString() ?: String.empty,
        source.readParcelable<Geolocation>(Geolocation::class.java.classLoader) ?: Geolocation(String.empty,String.empty)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(street)
        writeString(suite)
        writeString(city)
        writeString(zipCode)
        writeParcelable(geo, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Address> = object : Parcelable.Creator<Address> {
            override fun createFromParcel(source: Parcel): Address = Address(source)
            override fun newArray(size: Int): Array<Address?> = arrayOfNulls(size)
        }
    }
}

data class Geolocation(val lat: String, val lng: String) : Parcelable, ModelDomain<GeolocationEmbed>() {
    override fun toDB(): GeolocationEmbed = GeolocationEmbed(lat,lng)

    constructor(source: Parcel) : this(
        source.readString() ?: String.empty,
        source.readString() ?: String.empty
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(lat)
        writeString(lng)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Geolocation> = object : Parcelable.Creator<Geolocation> {
            override fun createFromParcel(source: Parcel): Geolocation = Geolocation(source)
            override fun newArray(size: Int): Array<Geolocation?> = arrayOfNulls(size)
        }
    }
}


data class Company(val name: String, val catchPhrase: String, val bs: String) : Parcelable, ModelDomain<CompanyEmbed>() {

    override fun toDB(): CompanyEmbed = CompanyEmbed(name,catchPhrase,bs)

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: String.empty,
        parcel.readString() ?: String.empty,
        parcel.readString() ?: String.empty
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(catchPhrase)
        parcel.writeString(bs)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Company> {
        override fun createFromParcel(parcel: Parcel): Company {
            return Company(parcel)
        }

        override fun newArray(size: Int): Array<Company?> {
            return arrayOfNulls(size)
        }
    }
}

data class Photo(val albumId: Int,
                 val id: Int,
                 val title: String,
                 val url: String,
                 val thumbnailUrl: String) : Parcelable, ModelDomain<PhotoDB>() {
    override fun toDB(): PhotoDB = PhotoDB(id,albumId,title,url,thumbnailUrl)

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: String.empty,
        parcel.readString() ?: String.empty,
        parcel.readString() ?: String.empty
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(albumId)
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeString(thumbnailUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Photo> {
        override fun createFromParcel(parcel: Parcel): Photo {
            return Photo(parcel)
        }

        override fun newArray(size: Int): Array<Photo?> {
            return arrayOfNulls(size)
        }
    }

}