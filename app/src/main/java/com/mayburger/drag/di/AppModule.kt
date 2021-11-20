package com.mayburger.drag.di

import android.app.Application
import android.content.Context
import com.mayburger.drag.data.PersistenceDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Singleton
    @Provides
    fun providePersistence(@ApplicationContext appContext: Context) = PersistenceDatabase.getDatabase(appContext)
}