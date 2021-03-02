package com.antonioleiva.data.repository

import com.antonioleiva.data.api.ApiUsers
import javax.inject.Inject

class ApiUsersRepository @Inject constructor(private val apiUsers: ApiUsers) {

    suspend fun getUsers() =  apiUsers.getUsers()

}