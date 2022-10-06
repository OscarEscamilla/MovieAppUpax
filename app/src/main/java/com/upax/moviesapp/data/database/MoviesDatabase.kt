package com.upax.moviesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.upax.moviesapp.data.database.dao.MovieDao
import com.upax.moviesapp.data.database.entitites.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}