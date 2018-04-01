package com.jollypanda.petrsudoors.core

import android.app.Application
import android.support.multidex.MultiDexApplication
import com.jollypanda.petrsudoors.core.di.component.CoreComponent
import com.jollypanda.petrsudoors.core.di.component.DaggerCoreComponent
import com.jollypanda.petrsudoors.core.di.module.AppModule
import com.jollypanda.petrsudoors.core.di.module.NetworkModule

/**
 * @author Yamushev Igor
 * @since  17.03.18
 */
class App : Application() {

    lateinit var coreComponent: CoreComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        initDI()
    }

    private fun initDI() {
        coreComponent = DaggerCoreComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .build()
    }

    companion object {
        lateinit var instance: App
    }
}