package com.fernandez.rico.jetpatck.data.db

import androidx.room.*
import com.fernandez.rico.jetpatck.utils.Constants

@Entity ( tableName = Constants.TABLE_NAMES.TB_POST)
data class PostDB(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "user_id") val user_id: Int,
    val title: String?=null,
    val body: String?=null
)

@Entity ( tableName = Constants.TABLE_NAMES.TB_PHOTO)
data class PhotoDB(
    @PrimaryKey val id: Int,
    val albumId: Int,
    val title: String?=null,
    val url: String?=null,
    val thumbnailUrl: String?=null
)


@Entity (tableName = Constants.TABLE_NAMES.TB_USER)
data class UserDB(
    @PrimaryKey val id: Int,
    val name: String,
    val username: String,
    val email: String,
    @Embedded val address: AddressEmbed,
    val phone: String,
    val website: String,
    @Embedded val company: CompanyEmbed
)


data class AddressEmbed(
    val street: String,
    val suite: String,
    val city: String,
    val zipCode: String,
    @Embedded val geo: GeolocationEmbed

)

data class GeolocationEmbed(@ColumnInfo(name = "latitude") val latitude: String,@ColumnInfo(name = "longitude") val longitude: String)
data class CompanyEmbed(@ColumnInfo(name = "company_name") val name: String, @ColumnInfo(name = "catch_phrase") val catchPhrase: String, val bs: String)
