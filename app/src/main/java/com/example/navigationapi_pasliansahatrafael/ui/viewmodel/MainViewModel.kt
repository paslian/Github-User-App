package com.example.navigationapi_pasliansahatrafael.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.navigationapi_pasliansahatrafael.data.remote.response.ItemsItem
import com.example.navigationapi_pasliansahatrafael.data.remote.response.SearchResponse
import com.example.navigationapi_pasliansahatrafael.data.remote.retrofit.ApiConfig
import com.example.navigationapi_pasliansahatrafael.setting.SettingPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {

    private val _item = MutableLiveData<List<ItemsItem>>()
    val githubItems: LiveData<List<ItemsItem>> = _item

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findItem(ITEM_ID)
    }

    fun findItem(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUsers(query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _item.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    companion object{
        private const val TAG = "MainViewModel"
        private const val ITEM_ID = "github"
    }
}