package com.jollypanda.doorsthingsapp.utils

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

/**
 * @author Yamushev Igor
 * @since  02.04.18
 */
fun <T> Single<Response<T>>.asRetrofitResponse() = this
    .map {
        if (!it.isSuccessful)
            throw Exception()
        it
    }
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<Response<T>>.asRetrofitBody() =
        this.asRetrofitResponse().map { it.body()!! }!!