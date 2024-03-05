package com.example.navigationapi_pasliansahatrafael.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationapi_pasliansahatrafael.data.FavoriteRepository
import com.example.navigationapi_pasliansahatrafael.data.database.entity.FavoriteEntity
import com.example.navigationapi_pasliansahatrafael.data.remote.response.DetailUserResponse
import com.example.navigationapi_pasliansahatrafael.data.remote.response.ItemsItem
import com.example.navigationapi_pasliansahatrafael.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {

    private val _userDetail = MutableLiveData<DetailUserResponse>()
    val userDetail: LiveData<DetailUserResponse> = _userDetail

    private val _followersList = MutableLiveData<List<ItemsItem>>()
    val followersList: LiveData<List<ItemsItem>> = _followersList

    private val _followingList = MutableLiveData<List<ItemsItem>>()
    val followingList: LiveData<List<ItemsItem>> = _followingList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val userDetail = response.body()
                    if (userDetail != null) {
                        _userDetail.value = userDetail
                    } else {
                        _errorMessage.value = "Data pengguna tidak ditemukan."
                    }
                } else {
                    _errorMessage.value = "Gagal mengambil data pengguna."
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Terjadi kesalahan dalam melakukan permintaan ke API."
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followersList.value = response.body()
                } else {
                    _errorMessage.value = "Gagal mengambil data pengikut."
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Terjadi kesalahan dalam melakukan permintaan ke API."
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followingList.value = response.body()
                } else {
                    _errorMessage.value = "Gagal mengambil data yang diikuti."
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Terjadi kesalahan dalam melakukan permintaan ke API."
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }


    fun addToRoom(favorite: FavoriteEntity) {
        mFavoriteRepository.insert(favorite)
    }

    fun deleteFromRoom(username: String) {
        mFavoriteRepository.delete(username)
    }

    fun getGithubByUsername(username: String): LiveData<FavoriteEntity> = mFavoriteRepository.getByUsername(username)


    companion object{
        private const val TAG = "DetailViewModel"
    }

}
