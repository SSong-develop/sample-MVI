package com.ssong_develop.samplemvi.data.api

import com.ssong_develop.samplemvi.data.entity.User

class ApiHelperImpl(private val apiService : ApiService) : ApiHelper {
    override suspend fun getUsers(): List<User> {
        return apiService.getusers()
    }
}