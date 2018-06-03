package com.jollypanda.doorsthingsapp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.google.android.gms.nearby.connection.Payload
import com.jollypanda.doorsthingsapp.data.remote.request.ACTION
import com.jollypanda.doorsthingsapp.data.remote.request.KeyRequest
import com.jollypanda.doorsthingsapp.data.remote.response.KeyResponse
import com.jollypanda.petrsudoors.core.di.inject

/**
 * @author Yamushev Igor
 * @since  31.03.18
 */
@StateStrategyType(SkipStrategy::class)
interface MainView : MvpView {
    fun showKeyResponse(endPointId: String, jsonResponse: String)
}

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    
    private val gson by inject { gson }
    private val keyModel by inject { keyModel }
    
//    var endPointId: String? = null
    
    private val endPointIdSet = mutableSetOf<String>()
    
    fun handlePayload(endpointId: String, payload: Payload) {
        val s = String(payload.asBytes()!!)
        val request = gson.fromJson<KeyRequest>(s, KeyRequest::class.java)
        when (request.action) {
            ACTION.GET_KEY -> getKeyByNumber(endpointId, request)
            ACTION.GET_KEY_BY_SCHEDULE -> getKeyBySchedule(endpointId, request)
            ACTION.RETURN_KEY -> returnKeyByNumber(endpointId, request)
        }
    }
    
    private fun getKeyByNumber(endPointId: String, request: KeyRequest) {
        keyModel.getKey(request.roomNumber!!, request.token)
            .map { Pair(endPointId, it) }
            .onErrorReturn { Pair(endPointId, KeyResponse(ACTION.GET_KEY, null, null, it.message)) }
            .subscribe(
                this::handleKeyResponse
            )
    }
    
    private fun getKeyBySchedule(endPointId: String, request: KeyRequest) {
        keyModel.getKeyBySchedule(request.time!!, request.token)
            .map { Pair(endPointId, it) }
            .onErrorReturn { Pair(endPointId, KeyResponse(ACTION.GET_KEY_BY_SCHEDULE,null, null, it.message)) }
            .subscribe(
                this::handleKeyResponse
            )
    }
    
    private fun returnKeyByNumber(endPointId: String, request: KeyRequest) {
        keyModel.returnKey(request.roomNumber!!, request.token)
            .map { Pair(endPointId, it) }
            .onErrorReturn { Pair(endPointId, KeyResponse(ACTION.RETURN_KEY, null, null, it.message)) }
            .subscribe(
                this::handleKeyResponse
            )
    }
    
    private fun handleKeyResponse(pair: Pair<String, KeyResponse>) {
        viewState.showKeyResponse(pair.first, gson.toJson(pair.second))
    }
    
    fun removeEndPoint(endpointId: String) {
        endPointIdSet.remove(endpointId)
    }
    
    fun addEndPointId(endpointId: String) {
        endPointIdSet.add(endpointId)
    }
    
}