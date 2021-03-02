package com.antonioleiva.cleanarchitecturesample.framework.api

import com.antonioleiva.data.api.ApiUsers
import com.antonioleiva.data.api.ApiUsersService
import com.antonioleiva.data.model.User
import retrofit2.Response
import javax.inject.Inject

class ApiUsersImpl @Inject constructor(private val apiUsersService: ApiUsersService) : ApiUsers {

    override suspend fun getUsers(): Response<List<User>> = apiUsersService.getUsers()

}