package com.jollypanda.doorsthingsapp.core.di.module

import com.jollypanda.doorsthingsapp.data.remote.Api
import com.jollypanda.doorsthingsapp.model.KeyModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Yamushev Igor
 * @since  03.04.18
 */
@Module
class ModelsModule {
    
    @Provides
    @Singleton
    fun provideKeyModel(api: Api) = KeyModel(api)
    
}