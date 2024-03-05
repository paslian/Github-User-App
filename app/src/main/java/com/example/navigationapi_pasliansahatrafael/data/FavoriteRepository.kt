package com.example.navigationapi_pasliansahatrafael.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.navigationapi_pasliansahatrafael.data.database.entity.FavoriteEntity
import com.example.navigationapi_pasliansahatrafael.data.database.room.FavoriteDao
import com.example.navigationapi_pasliansahatrafael.data.database.room.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> = mFavoriteDao.getAll()

    fun getByUsername(username: String): LiveData<FavoriteEntity> = mFavoriteDao.getByUsername(username)

    fun insert(favorite: FavoriteEntity) {
        executorService.execute { mFavoriteDao.add(favorite) }
    }

    fun delete(username: String) {
        executorService.execute { mFavoriteDao.deleteByUsername(username) }
    }
}