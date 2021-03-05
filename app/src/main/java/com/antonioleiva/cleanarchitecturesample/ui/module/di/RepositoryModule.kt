package com.antonioleiva.cleanarchitecturesample.ui.module.di

import com.antonioleiva.domain.ProjectRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

//    @Binds
//    @Singleton
//    fun bindCategoryRepository(categoryRepository: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    fun bindProjectRepository(projectRepository: ProjectRepositoryImpl): ProjectRepository
}