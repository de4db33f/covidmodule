package com.example.covidmodule.common.utils

import com.example.covidmodule.main_module.model.CovidModuleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideMainRepository(): CovidModuleRepository = CovidModuleRepository()
}