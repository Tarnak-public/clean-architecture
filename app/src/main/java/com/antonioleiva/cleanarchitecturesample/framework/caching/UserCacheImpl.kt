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
package com.antonioleiva.cleanarchitecturesample.framework.caching

import android.content.Context
import com.antonioleiva.cleanarchitecturesample.utils.FileManager
import com.antonioleiva.cleanarchitecturesample.utils.Serializer
import com.antonioleiva.data.cache.UserCache
import com.antonioleiva.data.exception.UserNotFoundException
import com.antonioleiva.data.newer.entity.UserEntity
import com.antonioleiva.domain.newer.executor.ThreadExecutor
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [UserCache] implementation.
 */
@Singleton
class UserCacheImpl @Inject internal constructor(
    context: Context?, serializer: Serializer?,
    fileManager: FileManager?, executor: ThreadExecutor?
) : UserCache {
    private val SETTINGS_FILE_NAME = this.javaClass.toString() + ".SETTINGS"
    private val context: Context
    private val cacheDir: File
    private val serializer: Serializer
    private val fileManager: FileManager
    private val threadExecutor: ThreadExecutor
    override fun get(userId: Int): Observable<UserEntity?>? {
        return Observable.create { emitter: ObservableEmitter<UserEntity> ->
            val userEntityFile = buildFile(userId)
            val fileContent = fileManager.readFileContent(userEntityFile)
            val userEntity = serializer.deserialize(fileContent, UserEntity::class.java)
            emitter.onNext(userEntity)
            emitter.onComplete()
        }
    }

    override fun put(userEntity: UserEntity?) {
        val userEntityFile = buildFile(userEntity?.userId)
        if (userEntity != null) {
            if (!isCached(userEntity.userId)) {
                val jsonString = serializer.serialize(userEntity, UserEntity::class.java)
                executeAsynchronously(CacheWriter(fileManager, userEntityFile, jsonString))
                setLastCacheUpdateTimeMillis()
            }
        }
    }

    override fun isCached(userId: Int): Boolean {
        val userEntityFile = buildFile(userId)
        return fileManager.exists(userEntityFile)
    }

    override val isExpired: Boolean
        get() {
            val currentTime = System.currentTimeMillis()
            val lastUpdateTime = lastCacheUpdateTimeMillis
            val expired = currentTime - lastUpdateTime > EXPIRATION_TIME
            if (expired) {
                evictAll()
            }
            return expired
        }

    override fun evictAll() {
        executeAsynchronously(CacheEvictor(fileManager, cacheDir))
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @param userId The id user to build the file.
     * @return A valid file.
     */
    private fun buildFile(userId: Int?): File {
        val fileNameBuilder = cacheDir.path +
                File.separator +
                DEFAULT_FILE_NAME +
                userId
        return File(fileNameBuilder)
    }

    /**
     * Set in millis, the last time the cache was accessed.
     */
    private fun setLastCacheUpdateTimeMillis() {
        val currentMillis = System.currentTimeMillis()
        fileManager.writeToPreferences(
            context, SETTINGS_FILE_NAME,
            SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis
        )
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private val lastCacheUpdateTimeMillis: Long
        get() = fileManager.getFromPreferences(
            context, SETTINGS_FILE_NAME,
            SETTINGS_KEY_LAST_CACHE_UPDATE
        )

    /**
     * Executes a [Runnable] in another Thread.
     *
     * @param runnable [Runnable] to execute
     */
    private fun executeAsynchronously(runnable: Runnable) {
        threadExecutor.execute(runnable)
    }

    /**
     * [Runnable] class for writing to disk.
     */
    private class CacheWriter internal constructor(
        private val fileManager: FileManager,
        private val fileToWrite: File,
        private val fileContent: String
    ) : Runnable {
        override fun run() {
            fileManager.writeToFile(fileToWrite, fileContent)
        }
    }

    /**
     * [Runnable] class for evicting all the cached files
     */
    private class CacheEvictor internal constructor(
        private val fileManager: FileManager,
        private val cacheDir: File
    ) : Runnable {
        override fun run() {
            fileManager.clearDirectory(cacheDir)
        }
    }

    companion object {
        private const val SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update"
        private const val DEFAULT_FILE_NAME = "user_"
        private const val EXPIRATION_TIME = (60 * 10 * 1000).toLong()
    }

    /**
     * Constructor of the class [com.antonioleiva.cleanarchitecturesample.framework.caching.UserCacheImpl].
     *
     * @param context     A
     * @param serializer  [Serializer] for object serialization.
     * @param fileManager [FileManager] for saving serialized objects to the file system.
     */
    init {
        require(!(context == null || serializer == null || fileManager == null || executor == null)) { "Invalid null parameter" }
        this.context = context.applicationContext
        cacheDir = this.context.cacheDir
        this.serializer = serializer
        this.fileManager = fileManager
        threadExecutor = executor
    }
}