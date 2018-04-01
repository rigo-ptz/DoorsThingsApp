package com.jollypanda.petrsudoors.core.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.jollypanda.doorsthingsapp.data.remote.Api
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
@Module
class NetworkModule {
    
    private val BASE_URL = "https://petrsu-doors.herokuapp.com/api/v1/"
    private val serverDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale("ru"))

    @Provides
    @Singleton
    fun provideApi(
            retrofitBuilder: Retrofit.Builder,
            clientBuilder: OkHttpClient.Builder
    ): Api {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        
        clientBuilder.addInterceptor(logging)

        return retrofitBuilder
            .client(clientBuilder.build())
            .build()
            .create(Api::class.java)
    }
    
    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder =
            OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
    
    @Provides
    @Singleton
    fun provideGson(): Gson {
        val builder = GsonBuilder()
            .registerTypeAdapter(Date::class.java,
                                 JsonDeserializer { json, typeOfT, context ->
                                     val dateString = json.asString
                                     val d: Date
                                     try {
                                         d = serverDateFormat.parse(dateString)
                                     } catch (e: ParseException){
                                         throw RuntimeException(e)
                                     }
                                     d
                                 })
        return builder.create()
    }
    
    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit.Builder =
            Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
}