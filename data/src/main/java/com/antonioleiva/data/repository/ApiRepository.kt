package com.antonioleiva.data.repository

import com.antonioleiva.data.api.ApiHelper
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getUsers() =  apiHelper.getUsers()

}