package com.ssong_develop.samplemvi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssong_develop.samplemvi.data.repository.MainRepository
import com.ssong_develop.samplemvi.ui.intent.MainIntent
import com.ssong_develop.samplemvi.ui.viewstate.MainState
import com.ssong_develop.samplemvi.util.APIState
import com.ssong_develop.samplemvi.util.StateAPICallback
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState>
        get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when(it){
                    is MainIntent.FetchUser -> fetchUserWithCall()
                }
            }
        }
    }

    private fun fetchUser(){
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try{
                MainState.Users(mainRepository.getUsers())
            }catch (e : Exception){
                MainState.Error(e.localizedMessage)
            }
        }
    }

    private fun fetchUserWithCall() {
        _state.value = MainState.Loading
        mainRepository.getUsersWithCall().enqueue(StateAPICallback { apiState ->
            when(apiState) {
                is APIState.Success -> _state.value = MainState.Users(apiState.body)
                is APIState.NoContents -> _state.value = MainState.Error("No Content in this api")
                is APIState.Fail -> _state.value = MainState.Error("Server connect fail")
            }
        })
    }
}