package com.upax.moviesapp.repository


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.inject.Deferred
import com.upax.moviesapp.data.model.Location
import com.upax.moviesapp.data.network.LocationsDataSource
import javax.inject.Inject

class LocationRepository @Inject constructor(private val locationsDataSource: LocationsDataSource) {


    suspend fun getAllLocations(): List<Location> {
        return locationsDataSource.getAllLocations()
    }


    suspend fun saveLocation(location: Location) {
        locationsDataSource.saveLocation(location)
    }

}
