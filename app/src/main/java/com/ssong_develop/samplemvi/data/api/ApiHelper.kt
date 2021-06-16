package com.ssong_develop.samplemvi.data.api

import com.ssong_develop.samplemvi.data.entity.User
import retrofit2.Call

interface ApiHelper {

    suspend fun getUsers() : List<User>

    fun getUsersWithCall() : Call<List<User>>
}