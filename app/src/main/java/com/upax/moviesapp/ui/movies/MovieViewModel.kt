package com.upax.moviesapp.ui.movies

import androidx.lifecycle.*
import com.upax.moviesapp.core.Resource
import com.upax.moviesapp.data.model.Movie
import com.upax.moviesapp.domain.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase): ViewModel() {

    val fetchScreenMovies = liveData<Resource<Triple<List<Movie>, List<Movie>, List<Movie>>>>(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            emit(Resource.Succes(getMoviesUseCase()))

        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

}

