package com.antonioleiva.cleanarchitecturesample.ui.module

import androidx.fragment.app.Fragment
import com.antonioleiva.cleanarchitecturesample.ui.main.view.EmptyFragment
import com.antonioleiva.cleanarchitecturesample.ui.main.view.ApiUsageFragment
import com.antonioleiva.cleanarchitecturesample.ui.main.view.StudentInfoFragment
import com.antonioleiva.cleanarchitecturesample.ui.main.view.StudentInfoListFragment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
abstract class FragmentFactoryModule {

    @Binds
    @IntoMap
    @FragmentKey(ApiUsageFragment::class)
    abstract fun bindExampleApiShowFragment(fragment: ApiUsageFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(EmptyFragment::class)
    abstract fun bindEmptyFragment(fragment: EmptyFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(StudentInfoFragment::class)
    abstract fun bindStudentInfoFragment(fragment: StudentInfoFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(StudentInfoListFragment::class)
    abstract fun bindStudentInfoListFragment(fragment: StudentInfoListFragment): Fragment
}