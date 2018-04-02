package com.jollypanda.doorsthingsapp.model

import com.jollypanda.doorsthingsapp.data.remote.Api
import com.jollypanda.doorsthingsapp.utils.asRetrofitResponse

/**
 * @author Yamushev Igor
 * @since  03.04.18
 */
class KeyModel(
    private val api: Api
) {
    
    fun getKey(number: String, token: String)=
        api.getKeyByNum(number, "JWT $token")
            .asRetrofitResponse()
    
    
    fun returnKey(number: String, token: String)=
            api.returnKeyByNum(number, "JWT $token")
                .asRetrofitResponse()
}