package com.example.navigationapi_pasliansahatrafael.data.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity (tableName = "favorite")
@Parcelize
class FavoriteEntity(
    @PrimaryKey(autoGenerate = false)
    @field:ColumnInfo(name = "username")
    var username: String = "",

    @field:ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null,
) : Parcelable