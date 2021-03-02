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
package com.antonioleiva.cleanarchitecturesample.ui.mapper

import com.antonioleiva.cleanarchitecturesample.ui.model.UserModel
import com.antonioleiva.domain.newer.User
import java.util.*
import javax.inject.Inject

/**
 * Mapper class used to transform [User] (in the domain layer) to [UserModel] in the
 * presentation layer.
 */
class UserModelDataMapper @Inject constructor() {
    /**
     * Transform a [User] into an [UserModel].
     *
     * @param user Object to be transformed.
     * @return [UserModel].
     */
    fun transform(user: User?): UserModel {
        requireNotNull(user) { "Cannot transform a null value" }
        val userModel = UserModel(user.userId)
        userModel.coverUrl = user.coverUrl
        userModel.fullName = user.fullName
        userModel.email = user.email
        userModel.description = user.description
        userModel.followers = user.followers
        return userModel
    }

    /**
     * Transform a Collection of [User] into a Collection of [UserModel].
     *
     * @param usersCollection Objects to be transformed.
     * @return List of [UserModel].
     */
    fun transform(usersCollection: Collection<User?>?): Collection<UserModel> {
        val userModelsCollection: Collection<UserModel>
        if (usersCollection != null && !usersCollection.isEmpty()) {
            userModelsCollection = ArrayList()
            for (user in usersCollection) {
                userModelsCollection.add(transform(user))
            }
        } else {
            userModelsCollection = emptyList()
        }
        return userModelsCollection
    }
}