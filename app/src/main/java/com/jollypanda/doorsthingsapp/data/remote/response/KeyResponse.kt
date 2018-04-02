package com.jollypanda.doorsthingsapp.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * @author Yamushev Igor
 * @since  02.04.18
 */
data class KeyResponse(
    @SerializedName("number") val roomNumber: String?,
    @SerializedName("floor") val roomFloor: String?,
    @SerializedName("reason") val failReason: String?
)