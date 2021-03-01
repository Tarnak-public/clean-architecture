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
package com.antonioleiva.data.cache

import com.antonioleiva.data.newer.entity.UserEntity
import io.reactivex.rxjava3.core.Observable

/**
 * An interface representing a user Cache.
 */
interface UserCache {
    /**
     * Gets an [Observable] which will emit a [UserEntity].
     *
     * @param userId The user id to retrieve data.
     */
    operator fun get(userId: Int): Observable<UserEntity?>?

    /**
     * Puts and element into the cache.
     *
     * @param userEntity Element to insert in the cache.
     */
    fun put(userEntity: UserEntity?)

    /**
     * Checks if an element (User) exists in the cache.
     *
     * @param userId The id used to look for inside the cache.
     * @return true if the element is cached, otherwise false.
     */
    fun isCached(userId: Int): Boolean

    /**
     * Checks if the cache is expired.
     *
     * @return true, the cache is expired, otherwise false.
     */
    val isExpired: Boolean

    /**
     * Evict all elements of the cache.
     */
    fun evictAll()
}