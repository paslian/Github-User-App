package com.example.navigationapi_pasliansahatrafael.helper

import com.example.navigationapi_pasliansahatrafael.data.database.entity.FavoriteEntity
import com.example.navigationapi_pasliansahatrafael.data.remote.response.ItemsItem

object mappingHelper {
    fun mapCursorToListUser(listItem: List<FavoriteEntity>): ArrayList<ItemsItem> {
        val userList = ArrayList<ItemsItem>()
        listItem.map { favorite ->
            userList.add(ItemsItem(favorite.username, favorite.avatarUrl?: ""))
        }
        return userList
    }
}