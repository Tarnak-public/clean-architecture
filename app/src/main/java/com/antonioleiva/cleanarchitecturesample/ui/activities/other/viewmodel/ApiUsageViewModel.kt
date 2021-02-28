package com.antonioleiva.cleanarchitecturesample.ui.activities.other.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.antonioleiva.data.model.User
import com.antonioleiva.data.repository.ApiRepository
import com.antonioleiva.cleanarchitecturesample.utils.NetworkHelper
import com.antonioleiva.cleanarchitecturesample.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import com.antonioleiva.cleanarchitecturesample.ui.shared.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiUsageViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _users = MutableLiveData<Resource<List<User>>>()
    val users: LiveData<Resource<List<User>>>
        get() = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _users.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                apiRepository.getUsers().let {
                    if (it.isSuccessful) {
                        _users.postValue(Resource.success(it.body()))
                    } else _users.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _users.postValue(Resource.error("No internet connection", null))
        }
    }
}