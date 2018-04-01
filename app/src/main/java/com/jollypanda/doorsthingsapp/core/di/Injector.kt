package com.jollypanda.petrsudoors.core.di

import com.google.gson.Gson
import com.jollypanda.petrsudoors.core.App
import javax.inject.Inject

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
inline fun <T> inject(crossinline block: Injector.() -> T): Lazy<T> = lazy { Injector().block() }

class Injector {

    @Inject
    lateinit var gson: Gson

    init {
        App.instance.coreComponent.injectTo(this)
    }
}