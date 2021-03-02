package com.antonioleiva.cleanarchitecturesample.ui.module.di

import com.antonioleiva.data.api.ApiUsers
import com.antonioleiva.cleanarchitecturesample.framework.api.ApiUsersImpl
import com.antonioleiva.data.api.ApiUsersService

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    private val BASE_URL_USERS = "https://5e510330f2c0d300147c034c.mockapi.io/"
    private val DEBUG = false

    @Provides
    fun provideBaseUsersUrl() = BASE_URL_USERS

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiUsersService(retrofit: Retrofit): ApiUsersService = retrofit.create(ApiUsersService::class.java)

    @Provides
    @Singleton
    fun provideApiUsers(apiUsers: ApiUsersImpl): ApiUsers = apiUsers

}
