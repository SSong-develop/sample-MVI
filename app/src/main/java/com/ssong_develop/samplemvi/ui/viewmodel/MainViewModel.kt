package com.ssong_develop.samplemvi.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.ssong_develop.samplemvi.data.repository.MainRepository

class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

}