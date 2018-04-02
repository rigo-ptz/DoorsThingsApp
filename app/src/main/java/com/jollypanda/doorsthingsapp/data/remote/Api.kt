package com.jollypanda.doorsthingsapp.data.remote

import com.jollypanda.doorsthingsapp.data.remote.response.KeyResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * @author Yamushev Igor
 * @since  02.04.18
 */
interface Api {
    
    @GET("api/v1/room/{roomNumber}/keys/get")
    fun getKeyByNum(@Path("roomNumber") roomNumber: String,
                    @Header("Authorization") token: String): Single<Response<KeyResponse>>
    
    @GET("api/v1/room/{roomNumber}/keys/return")
    fun returnKeyByNum(@Path("roomNumber") roomNumber: String,
                       @Header("Authorization") token: String): Single<Response<KeyResponse>>
    
}