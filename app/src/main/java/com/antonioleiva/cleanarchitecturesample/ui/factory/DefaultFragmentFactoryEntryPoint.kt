package com.antonioleiva.cleanarchitecturesample.ui.factory

import com.antonioleiva.cleanarchitecturesample.ui.factory.DefaultFragmentFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface DefaultFragmentFactoryEntryPoint {
    fun getFragmentFactory(): DefaultFragmentFactory
}
