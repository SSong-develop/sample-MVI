package com.ssong_develop.samplemvi.data.api

import com.ssong_develop.samplemvi.data.entity.User
import retrofit2.Call

class ApiHelperImpl(private val apiService : ApiService) : ApiHelper {
    override suspend fun getUsers(): List<User> {
        return apiService.getusers()
    }

    override fun getUsersWithCall(): Call<List<User>> {
        return apiService.getUsersWithCall()
    }
}