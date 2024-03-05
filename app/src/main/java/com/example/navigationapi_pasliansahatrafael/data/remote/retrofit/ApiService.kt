package com.example.navigationapi_pasliansahatrafael.data.remote.retrofit

import com.example.navigationapi_pasliansahatrafael.BuildConfig
import com.example.navigationapi_pasliansahatrafael.data.remote.response.DetailUserResponse
import com.example.navigationapi_pasliansahatrafael.data.remote.response.ItemsItem
import com.example.navigationapi_pasliansahatrafael.data.remote.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String
    ): Call<SearchResponse>

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>

}