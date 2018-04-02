package com.jollypanda.doorsthingsapp.data.remote.request

import com.google.gson.annotations.SerializedName

/**
 * @author Yamushev Igor
 * @since  03.04.18
 */
data class KeyRequest(
    @SerializedName("action") val action: ACTION,
    @SerializedName("token") val token: String,
    @SerializedName("num") val roomNumber: String
)

enum class ACTION(val value: String) {
    @SerializedName("get_key")
    GET_KEY("get_key"),
    @SerializedName("return_key")
    RETURN_KEY("return_key")
}