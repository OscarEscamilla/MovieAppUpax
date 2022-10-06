package com.upax.moviesapp.repository

import com.upax.moviesapp.data.database.dao.MovieDao
import com.upax.moviesapp.data.database.entitites.MovieEntity
import com.upax.moviesapp.data.database.entitites.toMovieList
import com.upax.moviesapp.data.model.Movie
import com.upax.moviesapp.data.network.MovieDataSource
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieDataSource: MovieDataSource,
    private val movieDao: MovieDao
) {

    suspend fun clearMovies() {
        movieDao.deleteAllMovies()
    }

    suspend fun getAllMoviesFromDatabase(): Triple<List<Movie>, List<Movie>, List<Movie>> {
        val movies = movieDao.getAllMovies()
        return Triple(
            movies.filter { it.movie_type == "upcoming" }.toMovieList(),
            movies.filter { it.movie_type == "top_rated" }.toMovieList(),
            movies.filter { it.movie_type == "popular" }.toMovieList()
        )
    }

    suspend fun getAllMoviesFromApi(): Triple<List<Movie>, List<Movie>, List<Movie>> {
        return Triple(
            movieDataSource.getUpcomingMovies(),
            movieDataSource.getTopRatedMovies(),
            movieDataSource.getPopularMovies()
        )
    }

    suspend fun saveMovies(movies: List<MovieEntity>) {
        movieDao.insertAllMovies(movies)
    }

}