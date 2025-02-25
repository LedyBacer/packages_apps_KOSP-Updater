package com.krypton.updater.di

import android.content.Context
import android.os.UpdateEngine

import androidx.room.Room

import com.krypton.updater.UpdaterApp
import com.krypton.updater.data.room.AppDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "updater_database"
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideApplicationScope(@ApplicationContext context: Context) =
        (context as UpdaterApp).applicationScope

    @Provides
    fun provideUpdateEngine() = UpdateEngine()
}