package com.mindorks.framework.mvvm.presentation.ui.factory

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface DefaultFragmentFactoryEntryPoint {
    fun getFragmentFactory(): DefaultFragmentFactory
}
