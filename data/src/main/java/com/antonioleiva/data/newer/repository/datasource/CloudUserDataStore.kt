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
package com.antonioleiva.data.newer.repository.datasource

import com.antonioleiva.data.cache.UserCache
import com.antonioleiva.data.newer.entity.UserEntity
import com.antonioleiva.data.newer.net.RestApi
import io.reactivex.rxjava3.core.Observable

/**
 * [UserDataStore] implementation based on connections to the api (Cloud).
 */
class CloudUserDataStore
/**
 * Construct a [UserDataStore] based on connections to the api (Cloud).
 *
 * @param restApi The [RestApi] implementation to use.
 * @param userCache A [UserCache] to cache data retrieved from the api.
 */(private val restApi: RestApi, private val userCache: UserCache) : UserDataStore {
    override fun userEntityList(): Observable<List<UserEntity?>?>? {
        return restApi.userEntityList()
    }

    override fun userEntityDetails(userId: Int): Observable<UserEntity?>? {
        return restApi.userEntityById(userId)
            ?.doOnNext { userEntity: UserEntity? -> userCache.put(userEntity) }
    }
}