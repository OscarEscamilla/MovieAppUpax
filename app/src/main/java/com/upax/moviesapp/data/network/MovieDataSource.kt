package com.upax.moviesapp.data.network

import com.upax.moviesapp.data.model.Movie
import com.upax.moviesapp.utils.AppConstants
import javax.inject.Inject

class MovieDataSource @Inject constructor(private val api: MovieApiClient) {

    suspend fun getUpcomingMovies(): List<Movie> {
        return api.getUpcomingMovies(AppConstants.API_KEY).results
    }

    suspend fun getTopRatedMovies(): List<Movie> {
        return api.getTopRatedMovies(AppConstants.API_KEY).results
    }

    suspend fun getPopularMovies(): List<Movie> {
        return api.getPopularMovies(AppConstants.API_KEY).results
    }

}