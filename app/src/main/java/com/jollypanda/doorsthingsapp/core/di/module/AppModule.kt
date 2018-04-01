package com.jollypanda.petrsudoors.core.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext() = context
}