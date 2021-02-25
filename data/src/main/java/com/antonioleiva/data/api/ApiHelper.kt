package com.antonioleiva.data.api

import com.antonioleiva.data.model.User
import retrofit2.Response

interface ApiHelper {

    suspend fun getUsers(): Response<List<User>>
}