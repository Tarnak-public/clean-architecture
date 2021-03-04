package com.antonioleiva.cleanarchitecturesample.framework.newer

import android.content.Context
import android.net.ConnectivityManager
import com.antonioleiva.cleanarchitecturesample.framework.newer.ApiConnection.Companion.createGET
import com.antonioleiva.data.newer.exception.NetworkConnectionException
import com.antonioleiva.data.newer.entity.UserEntity
import com.antonioleiva.data.newer.mapper.UserEntityJsonMapper
import com.antonioleiva.data.newer.net.RestApi
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import java.net.MalformedURLException

/**
 * [RestApi] implementation for retrieving data from the network.
 */
class RestApiImpl(context: Context?, userEntityJsonMapper: UserEntityJsonMapper?) : RestApi {
    private val context: Context
    private val userEntityJsonMapper: UserEntityJsonMapper
    override fun userEntityList(): Observable<List<UserEntity?>?>? {
        return Observable.create { emitter: ObservableEmitter<List<UserEntity?>?> ->
            if (isThereInternetConnection) {
                try {
                    val responseUserEntities = userEntitiesFromApi
                    if (responseUserEntities != null) {
                        emitter.onNext(
                            userEntityJsonMapper.transformUserEntityCollection(
                                responseUserEntities
                            )
                        )
                        emitter.onComplete()
                    } else {
                        emitter.onError(NetworkConnectionException())
                    }
                } catch (e: Exception) {
                    emitter.onError(
                        NetworkConnectionException(
                            e.cause
                        )
                    )
                }
            } else {
                emitter.onError(NetworkConnectionException())
            }
        }
    }

    override fun userEntityById(userId: Int): Observable<UserEntity?>? {
        return Observable.create { emitter: ObservableEmitter<UserEntity?> ->
            if (isThereInternetConnection) {
                try {
                    val responseUserDetails = getUserDetailsFromApi(userId)
                    if (responseUserDetails != null) {
                        emitter.onNext(userEntityJsonMapper.transformUserEntity(responseUserDetails))
                        emitter.onComplete()
                    } else {
                        emitter.onError(NetworkConnectionException())
                    }
                } catch (e: Exception) {
                    emitter.onError(
                        NetworkConnectionException(
                            e.cause
                        )
                    )
                }
            } else {
                emitter.onError(NetworkConnectionException())
            }
        }
    }

    @get:Throws(MalformedURLException::class)
    private val userEntitiesFromApi: String?
        get() = createGET(RestApi.API_URL_GET_USER_LIST).requestSyncCall()

    @Throws(MalformedURLException::class)
    private fun getUserDetailsFromApi(userId: Int): String? {
        val apiUrl = RestApi.API_URL_GET_USER_DETAILS + userId + ".json"
        return createGET(apiUrl).requestSyncCall()
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private val isThereInternetConnection: Boolean
        get() {
            val isConnected: Boolean
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting
            return isConnected
        }

    /**
     * Constructor of the class
     *
     * @param context [android.content.Context].
     * @param userEntityJsonMapper [UserEntityJsonMapper].
     */
    init {
        require(!(context == null || userEntityJsonMapper == null)) { "The constructor parameters cannot be null!!!" }
        this.context = context.applicationContext
        this.userEntityJsonMapper = userEntityJsonMapper
    }
}