package com.jollypanda.doorsthingsapp.data.remote.request

import com.google.gson.annotations.SerializedName

/**
 * @author Yamushev Igor
 * @since  03.04.18
 */
data class KeyRequest(
    @SerializedName("action") val action: ACTION,
    @SerializedName("token") val token: String,
    @SerializedName("num") val roomNumber: String?,
    @SerializedName("time") val time: String?
)

enum class ACTION(val value: String) {
    @SerializedName("get_key")
    GET_KEY("get_key"),
    @SerializedName("get_key_by_schedule")
    GET_KEY_BY_SCHEDULE("get_key_by_schedule"),
    @SerializedName("return_key")
    RETURN_KEY("return_key")
}