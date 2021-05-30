package com.ssong_develop.samplemvi.data.api

import com.ssong_develop.samplemvi.data.entity.User
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getusers() : List<User>
}