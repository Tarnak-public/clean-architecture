package com.mindorks.framework.mvvm.presentation.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mindorks.framework.mvvm.data.model.User
import com.mindorks.framework.mvvm.data.repository.ApiRepository
import com.mindorks.framework.mvvm.utils.NetworkHelper
import com.mindorks.framework.mvvm.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.supercharge.fragmentfactoryandhilt.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmptyViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

}