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
package com.antonioleiva.data.newer.entity

import com.google.gson.annotations.SerializedName

/**
 * User Entity used in the data layer.
 */
class UserEntity {
    @SerializedName("id")
    var userId : Int = 0

    @SerializedName("cover_url")
    val coverUrl: String? = null

    @SerializedName("full_name")
    var fullname: String? = null

    @SerializedName("description")
    val description: String? = null

    @SerializedName("followers")
    val followers = 0

    @SerializedName("email")
    val email: String? = null
}