package com.upax.moviesapp.di

import android.content.Context
import android.provider.SyncStateContract
import androidx.room.Room
import com.upax.moviesapp.data.database.MoviesDatabase
import com.upax.moviesapp.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MoviesDatabase::class.java, AppConstants.MOVIES_DATABASE).build()

    @Singleton
    @Provides
    fun provideMovieDao(db: MoviesDatabase) = db.getMovieDao()

}