package com.upax.moviesapp.ui.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.upax.moviesapp.core.Resource
import com.upax.moviesapp.data.model.Location
import com.upax.moviesapp.data.model.Movie
import com.upax.moviesapp.domain.GetLocationsUseCase
import com.upax.moviesapp.domain.SaveLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase
) : ViewModel() {

    val locations = liveData<Resource<List<Location>>>(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            emit(Resource.Succes(getLocationsUseCase()))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

}