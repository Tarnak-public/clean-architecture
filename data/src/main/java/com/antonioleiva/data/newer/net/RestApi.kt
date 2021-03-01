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
package com.antonioleiva.data.newer.net

import com.antonioleiva.data.newer.entity.UserEntity
import io.reactivex.rxjava3.core.Observable

/**
 * RestApi for retrieving data from the network.
 */
interface RestApi {
    /**
     * Retrieves an [Observable] which will emit a List of [UserEntity].
     */
    fun userEntityList(): Observable<List<UserEntity?>?>?

    /**
     * Retrieves an [Observable] which will emit a [UserEntity].
     *
     * @param userId The user id used to get user data.
     */
    fun userEntityById(userId: Int): Observable<UserEntity?>?

    companion object {
        const val API_BASE_URL =
            "https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture/"

        /** Api url for getting all users  */
        const val API_URL_GET_USER_LIST = API_BASE_URL + "users.json"

        /** Api url for getting a user profile: Remember to concatenate id + 'json'  */
        const val API_URL_GET_USER_DETAILS = API_BASE_URL + "user_"
    }
}