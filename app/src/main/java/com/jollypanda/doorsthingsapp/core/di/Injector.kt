package com.jollypanda.petrsudoors.core.di

import com.google.gson.Gson
import com.jollypanda.doorsthingsapp.model.KeyModel
import com.jollypanda.petrsudoors.core.App
import javax.inject.Inject

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
inline fun <T> inject(crossinline block: Injector.() -> T): Lazy<T> = lazy { Injector.getInstance().block() }

class Injector private constructor() {

    @Inject
    lateinit var gson: Gson
    
    @Inject
    lateinit var keyModel: KeyModel

    init {
        App.instance.coreComponent.injectTo(this)
    }
    
    companion object {
        private var mInstance: Injector? = null
        
        fun getInstance(): Injector {
            if (mInstance == null) mInstance = Injector()
            return mInstance!!
        }
    }
}