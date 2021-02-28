package com.antonioleiva.cleanarchitecturesample.ui.activities.other.navigator

import androidx.annotation.IdRes
import androidx.navigation.NavController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {

    lateinit var navController: NavController

    fun navigate(@IdRes navigationId: Int) {
        navController.navigate(navigationId)
    }

    fun navigateBack() {
        navController.popBackStack()
    }
}
