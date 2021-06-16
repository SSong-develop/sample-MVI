package com.ssong_develop.samplemvi.util

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

/**
 * @author pluu
 * https://github.com/Pluu/Retrofit-State-Callback
 *
 *
 */
class StateAPICallback<T>(private val callback : (APIState<T>) -> Unit) : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        when{
            response.isSuccessful && response.code() == HttpURLConnection.HTTP_NO_CONTENT -> {
                callback.invoke(APIState.NoContents())
            }
            response.isSuccessful -> {
                response.body()?.let {
                    callback.invoke(APIState.Success(it))
                } ?: callback.invoke(APIState.Fail())
            }
            else -> callback.invoke(APIState.Fail())
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        callback.invoke(APIState.Fail())
    }
}

/**
 * 제네릭을 out으로 잡은 이유에 대해서 좀 생각하면 좋을거 같습니다.
 * out -> 형식을 특정 형식과 그 하위 형식으로 제한하는 공변이다.
 * in -> 형식을 특정 형식과 그 상위 형식으로 제한하는 반변이다.
 */
sealed class APIState<out T>{
    class Success<out T>(val body : T) : APIState<T>()
    class NoContents<out T> : APIState<T>()
    class Fail<out T> : APIState<T>()
}