package com.example.navigationapi_pasliansahatrafael.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationapi_pasliansahatrafael.data.FavoriteRepository
import com.example.navigationapi_pasliansahatrafael.data.database.entity.FavoriteEntity

class FavoriteViewModel(application: Application) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllGithub(): LiveData<List<FavoriteEntity>> = mFavoriteRepository.getAllFavorite()

}