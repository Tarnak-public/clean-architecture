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
package com.antonioleiva.domain.newer.repository

import com.antonioleiva.domain.newer.User
import io.reactivex.rxjava3.core.Observable

/**
 * Interface that represents a Repository for getting [User] related data.
 */
interface UserRepository {
    /**
     * Get an [Observable] which will emit a List of [User].
     */
    fun users(): Observable<List<User?>?>?

    /**
     * Get an [Observable] which will emit a [User].
     *
     * @param userId The user id used to retrieve user data.
     */
    fun user(userId: Int): Observable<User?>?
}