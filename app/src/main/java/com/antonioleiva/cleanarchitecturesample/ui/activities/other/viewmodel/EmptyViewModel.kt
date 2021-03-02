package com.antonioleiva.cleanarchitecturesample.ui.activities.other.viewmodel

import com.antonioleiva.data.repository.ApiUsersRepository
import com.antonioleiva.cleanarchitecturesample.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import com.antonioleiva.cleanarchitecturesample.ui.factory.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class EmptyViewModel @Inject constructor(
    private val apiUsersRepository: ApiUsersRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

}