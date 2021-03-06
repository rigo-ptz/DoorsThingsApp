package com.jollypanda.doorsthingsapp.model

import com.jollypanda.doorsthingsapp.data.remote.Api
import com.jollypanda.doorsthingsapp.utils.asRetrofitBody

/**
 * @author Yamushev Igor
 * @since  03.04.18
 */
class KeyModel(
    private val api: Api
) {
    
    fun getKey(number: String, token: String) =
        api.getKeyByNum(number, "JWT $token")
            .asRetrofitBody()
    
    fun getKeyBySchedule(date: String, token: String) =
            api.getKeyBySchedule(date, "JWT $token")
                .asRetrofitBody()
    
    
    fun returnKey(number: String, token: String) =
            api.returnKeyByNum(number, "JWT $token")
                .asRetrofitBody()
}