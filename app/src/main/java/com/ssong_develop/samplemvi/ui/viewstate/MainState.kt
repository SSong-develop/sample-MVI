package com.ssong_develop.samplemvi.ui.viewstate

import com.ssong_develop.samplemvi.data.entity.User

/**
 * Each State can be loaded on to the view by the user intents
 */
sealed class MainState {
    object Idle : MainState()
    object Loading : MainState()
    data class Users(val user : List<User>) : MainState()
    data class Error(val error: String?) : MainState()
}