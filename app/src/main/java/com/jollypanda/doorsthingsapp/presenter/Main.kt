package com.jollypanda.doorsthingsapp.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.google.android.gms.nearby.connection.Payload
import com.jollypanda.doorsthingsapp.data.remote.request.ACTION
import com.jollypanda.doorsthingsapp.data.remote.request.KeyRequest
import com.jollypanda.petrsudoors.core.di.inject

/**
 * @author Yamushev Igor
 * @since  31.03.18
 */
@StateStrategyType(SkipStrategy::class)
interface MainView : MvpView {

}

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    
    private val gson by inject { gson }
    
    var endPointId: String? = null
    
    fun validateInput(endpointId: String, payload: Payload) {
        val s = String(payload.asBytes()!!)
        val request = gson.fromJson<KeyRequest>(s, KeyRequest::class.java)
        when (request.action) {
            ACTION.GET_KEY -> Log.e("VALIDATE", "GET")
            ACTION.RETURN_KEY -> Log.e("VALIDATE", "RETURN")
        }
    }
    
}