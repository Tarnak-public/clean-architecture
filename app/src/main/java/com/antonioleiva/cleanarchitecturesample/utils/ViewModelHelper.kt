package com.antonioleiva.cleanarchitecturesample.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

//class ViewModelHelper {

/*
request({request.getData()}) {
    //use data
}
 */
fun <T : Any> ViewModel.request(request: suspend () -> (T), result: (T) -> (Unit) = {}) {
    viewModelScope.launch {
        result(request())
    }
}
//}