package com.ssong_develop.samplemvi.data.repository

import com.ssong_develop.samplemvi.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getUsers() = apiHelper.getUsers()

    fun getUsersWithCall() = apiHelper.getUsersWithCall()
}