package com.upax.moviesapp.domain

import com.upax.moviesapp.repository.LocationRepository
import com.upax.moviesapp.data.model.Location
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(private val repository: LocationRepository) {
    operator fun invoke(): List<Location> {
        return repository.getAllLocations()
    }
}