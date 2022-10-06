package com.upax.moviesapp.domain

import com.upax.moviesapp.repository.LocationRepository
import com.upax.moviesapp.data.model.Location
import javax.inject.Inject

class SaveLocationUseCase @Inject constructor(private val repository: LocationRepository) {
    suspend operator fun invoke(location: Location) {
        repository.saveLocation(location)
    }
}