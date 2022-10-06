package com.upax.moviesapp.domain

import com.upax.moviesapp.core.InternetCheck
import com.upax.moviesapp.repository.MovieRepository
import com.upax.moviesapp.data.database.entitites.toMovieEntity
import com.upax.moviesapp.data.model.Movie
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(): Triple<List<Movie>, List<Movie>, List<Movie>> {
        return if (InternetCheck.isNetworkAvailable()){
            val movies = repository.getAllMoviesFromApi()
            repository.clearMovies()
            repository.saveMovies(movies.first.map { it.toMovieEntity("upcoming") })
            repository.saveMovies(movies.second.map { it.toMovieEntity("top_rated") })
            repository.saveMovies(movies.third.map { it.toMovieEntity("popular") })
            movies
        }else{
            repository.getAllMoviesFromDatabase()
        }
    }
}