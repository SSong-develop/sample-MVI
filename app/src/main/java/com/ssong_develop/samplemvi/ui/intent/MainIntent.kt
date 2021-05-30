package com.ssong_develop.samplemvi.ui.intent

sealed class MainIntent {
    object FetchUser : MainIntent()
}