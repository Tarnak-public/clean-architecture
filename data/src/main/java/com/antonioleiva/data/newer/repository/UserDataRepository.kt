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
package com.antonioleiva.data.newer.repository

import com.antonioleiva.data.newer.entity.UserEntity
import com.antonioleiva.data.newer.mapper.UserEntityDataMapper
import com.antonioleiva.data.newer.repository.datasource.UserDataStoreFactory
import com.antonioleiva.domain.newer.User
import com.antonioleiva.domain.newer.repository.UserRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Function
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [UserRepository] for retrieving user data.
 */
@Singleton
class UserDataRepository
/**
 * Constructs a [UserRepository].
 *
 * @param dataStoreFactory A factory to construct different data source implementations.
 * @param userEntityDataMapper [UserEntityDataMapper].
 */ @Inject internal constructor(
    private val userDataStoreFactory: UserDataStoreFactory,
    private val userEntityDataMapper: UserEntityDataMapper
) : UserRepository {
    override fun users(): Observable<List<User?>?>? {
        //we always get all users from the cloud
        val userDataStore = userDataStoreFactory.createCloudDataStore()
        return userDataStore!!.userEntityList()!!.map { userEntityCollection: List<UserEntity?>? ->
            userEntityDataMapper.transform(userEntityCollection)
        }
    }

    override fun user(userId: Int): Observable<User?>? {
        val userDataStore = userDataStoreFactory.create(userId)
        return userDataStore!!.userEntityDetails(userId)!!
            .map(Function { userEntity: UserEntity? -> userEntityDataMapper.transform(userEntity) })
    }
}