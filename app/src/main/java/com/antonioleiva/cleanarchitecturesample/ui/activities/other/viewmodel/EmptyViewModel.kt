package com.antonioleiva.cleanarchitecturesample.ui.activities.other.viewmodel

import com.antonioleiva.data.repository.ApiRepository
import com.antonioleiva.cleanarchitecturesample.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import com.antonioleiva.cleanarchitecturesample.ui.shared.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class EmptyViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

}