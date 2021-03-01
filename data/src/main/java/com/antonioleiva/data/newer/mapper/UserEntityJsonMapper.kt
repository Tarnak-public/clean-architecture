/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.antonioleiva.data.newer.mapper

import com.antonioleiva.data.newer.entity.UserEntity
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

/**
 * Class used to transform from Strings representing json to valid objects.
 */
class UserEntityJsonMapper @Inject constructor() {
    private val gson: Gson

    /**
     * Transform from valid json string to [UserEntity].
     *
     * @param userJsonResponse A json representing a user profile.
     * @return [UserEntity].
     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
     */
    @Throws(JsonSyntaxException::class)
    fun transformUserEntity(userJsonResponse: String?): UserEntity {
        val userEntityType = object : TypeToken<UserEntity?>() {}.type
        return gson.fromJson(userJsonResponse, userEntityType)
    }

    /**
     * Transform from valid json string to List of [UserEntity].
     *
     * @param userListJsonResponse A json representing a collection of users.
     * @return List of [UserEntity].
     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
     */
    @Throws(JsonSyntaxException::class)
    fun transformUserEntityCollection(userListJsonResponse: String?): List<UserEntity> {
        val listOfUserEntityType = object : TypeToken<List<UserEntity?>?>() {}.type
        return gson.fromJson(userListJsonResponse, listOfUserEntityType)
    }

    init {
        gson = Gson()
    }
}