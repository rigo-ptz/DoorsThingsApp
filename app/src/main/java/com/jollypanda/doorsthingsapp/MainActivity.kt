package com.jollypanda.doorsthingsapp

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import com.arellomobile.mvp.MvpActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.jollypanda.doorsthingsapp.presenter.MainPresenter
import com.jollypanda.doorsthingsapp.presenter.MainView


/**
 * Skeleton of an Android Things activity.
 *
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * val service = PeripheralManagerService()
 * val mLedGpio = service.openGpio("BCM6")
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
 * mLedGpio.value = true
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 *
 */
class MainActivity : MvpActivity(), MainView {
    
    @InjectPresenter
    lateinit var presenter: MainPresenter
    
    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        
        override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
            Log.e("NEARBY", "connectionLifecycleCallback onConnectionInitiated")
            // Automatically accept the connection on both sides.
            Nearby.getConnectionsClient(this@MainActivity).acceptConnection(endpointId, payloadCallback)
        }
        
        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            when (result.status.statusCode) {
                ConnectionsStatusCodes.STATUS_OK -> {
                    presenter.endPointId = endpointId
                    Log.e("NEARBY", "connectionLifecycleCallback onConnectionResult OK")
                }
                
                ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED -> {
                    Log.e("NEARBY", "connectionLifecycleCallback onConnectionResult REJECTED")
                }
                
                ConnectionsStatusCodes.STATUS_ERROR -> {
                    Log.e("NEARBY", "connectionLifecycleCallback onConnectionResult ERROR")
                }
            }
        }
        
        override fun onDisconnected(endpointId: String) {
            // We've been disconnected from this endpoint. No more data can be
            // sent or received.
            presenter.endPointId = null
            Log.e("NEARBY", "connectionLifecycleCallback onDisconnected")
        }
    }
    
    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            Log.e("NEARBY", "payloadCallback onPayloadReceived")
            Log.e("NEARBY+",  "$endpointId sended: ${String(payload.asBytes()!!)}")
            presenter.validateInput(payload)
        }
        
        override fun onPayloadTransferUpdate(payloadId: String, update: PayloadTransferUpdate) {
            val staus = when(update.status){
                PayloadTransferUpdate.Status.FAILURE -> "FAILURE"
                PayloadTransferUpdate.Status.IN_PROGRESS -> "IN_PROGRESS"
                PayloadTransferUpdate.Status.SUCCESS -> "SUCCESS"
                else -> "not defined"
            }
            Log.e("NEARBY", "payloadCallback onPayloadTransferUpdate status::${staus}")
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWiFi()
        startAdvertising()
    }
    
    override fun onStop() {
        Log.e("MAIN", "onStop")
        presenter.endPointId?.apply {
            Nearby.getConnectionsClient(this@MainActivity).disconnectFromEndpoint(this)
        }
        Nearby.getConnectionsClient(this).stopAdvertising()
        super.onStop()
    }
    
    private fun initWiFi() {
        val wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.isWifiEnabled = true
    }
    
    private fun startAdvertising() {
        Nearby.getConnectionsClient(this).stopAdvertising()
        Nearby.getConnectionsClient(this)
            .startAdvertising(
                "com.jollypanda.doorsthingsapp",
                "com.jollypanda.doorsthingsappID1",
                connectionLifecycleCallback,
                AdvertisingOptions(Strategy.P2P_STAR)
                /*AdvertisingOptions.Builder()
                    .setStrategy(Strategy.P2P_POINT_TO_POINT)
                    .build()*/
            )
            .addOnSuccessListener {
                Log.e("NEARBY", "startAdvertising SUCCESS")
            }
            .addOnFailureListener {
                // We were unable to start advertising.
                Log.e("NEARBY", "startAdvertising FAIL")
                it.printStackTrace()
            }
    }
    
}
