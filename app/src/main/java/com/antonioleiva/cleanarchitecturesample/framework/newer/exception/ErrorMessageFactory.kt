/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.antonioleiva.cleanarchitecturesample.framework.newer.exception

import android.content.Context
import com.antonioleiva.cleanarchitecturesample.R
import com.antonioleiva.data.newer.exception.NetworkConnectionException
import com.antonioleiva.data.newer.exception.UserNotFoundException

/**
 * Factory used to create error messages from an Exception as a condition.
 */
object ErrorMessageFactory {
    /**
     * Creates a String representing an error message.
     *
     * @param context   Context needed to retrieve string resources.
     * @param exception An exception used as a condition to retrieve the correct error message.
     * @return [String] an error message.
     */
    fun create(context: Context, exception: Exception?): String {
        var message = context.getString(R.string.exception_message_generic)
        if (exception is NetworkConnectionException) {
            message = context.getString(R.string.exception_message_no_connection)
        } else if (exception is UserNotFoundException) {
            message = context.getString(R.string.exception_message_user_not_found)
        }
        return message
    }
}