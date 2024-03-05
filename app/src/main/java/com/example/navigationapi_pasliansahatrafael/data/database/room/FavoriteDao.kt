package com.example.navigationapi_pasliansahatrafael.data.database.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.navigationapi_pasliansahatrafael.data.database.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert
    fun add(favorite: FavoriteEntity)

    @Query("DELETE FROM favorite WHERE username = :username")
    fun deleteByUsername(username: String)

    @Query("SELECT * from favorite")
    fun getAll(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * from favorite WHERE username = :username")
    fun getByUsername(username: String): LiveData<FavoriteEntity>

}