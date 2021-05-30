package com.ssong_develop.samplemvi.data.api

import com.ssong_develop.samplemvi.data.entity.User

interface ApiHelper {

    suspend fun getUsers() : List<User>
}