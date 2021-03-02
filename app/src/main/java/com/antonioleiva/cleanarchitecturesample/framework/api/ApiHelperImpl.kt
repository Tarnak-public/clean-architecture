package com.antonioleiva.cleanarchitecturesample.framework.api

import com.antonioleiva.data.api.ApiHelper
import com.antonioleiva.data.api.ApiService
import com.antonioleiva.data.model.User
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): Response<List<User>> = apiService.getUsers()

}