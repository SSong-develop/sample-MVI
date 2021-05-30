package com.ssong_develop.samplemvi.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ssong_develop.samplemvi.data.api.ApiHelper
import com.ssong_develop.samplemvi.data.repository.MainRepository
import com.ssong_develop.samplemvi.ui.viewmodel.MainViewModel
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}