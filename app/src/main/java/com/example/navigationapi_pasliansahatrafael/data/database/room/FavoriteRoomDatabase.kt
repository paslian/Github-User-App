package com.example.navigationapi_pasliansahatrafael.data.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.navigationapi_pasliansahatrafael.data.database.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class FavoriteRoomDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var instance: FavoriteRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FavoriteRoomDatabase {
            if (instance == null) {
                synchronized(FavoriteRoomDatabase::class.java) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        FavoriteRoomDatabase::class.java, "Favorite.db")
                        .build()
                }
            }
            return instance as FavoriteRoomDatabase
        }
    }
}
