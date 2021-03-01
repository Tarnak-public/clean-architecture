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
package com.antonioleiva.cleanarchitecturesample.framework

import android.content.Context
import com.antonioleiva.cleanarchitecturesample.framework.api.RestApiImpl
import com.antonioleiva.data.cache.UserCache
import com.antonioleiva.data.newer.mapper.UserEntityJsonMapper
import com.antonioleiva.data.newer.net.RestApi
import com.antonioleiva.data.newer.repository.datasource.CloudUserDataStore
import com.antonioleiva.data.newer.repository.datasource.DiskUserDataStore
import com.antonioleiva.data.newer.repository.datasource.UserDataStore
import com.antonioleiva.data.newer.repository.datasource.UserDataStoreFactory
import io.reactivex.rxjava3.annotations.NonNull
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Factory that creates different implementations of [UserDataStore].
 */
@Singleton
class UserDataStoreFactoryImpl @Inject internal constructor(
    context: @NonNull Context?,
    private val userCache: @NonNull UserCache?
) : UserDataStoreFactory {
    private val context: Context = context!!.applicationContext

    /**
     * Create [UserDataStore] from a user id.
     */
    override fun create(userId: Int): UserDataStore? {
        return if (!userCache!!.isExpired && userCache.isCached(userId)) {
            DiskUserDataStore(userCache)
        } else {
            createCloudDataStore()
        }
    }

    /**
     * Create [UserDataStore] to retrieve data from the Cloud.
     */
    override fun createCloudDataStore(): UserDataStore? {
        val userEntityJsonMapper = UserEntityJsonMapper()
        val restApi: RestApi = RestApiImpl(context, userEntityJsonMapper)
        return CloudUserDataStore(restApi, userCache!!)
    }

}