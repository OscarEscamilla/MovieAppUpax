package com.upax.moviesapp.data.network

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.upax.moviesapp.data.model.Location
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationsDataSource @Inject constructor(private val locationsRef: CollectionReference){

    suspend fun getAllLocations(): List<Location> {
        val listLocation = mutableListOf<Location>()
        val querySnapshot = locationsRef.get().await()
        for(location in querySnapshot){
            location.toObject(Location::class.java).let { listLocation.add(it) }
        }
        return listLocation.toList()
    }

    suspend fun saveLocation(location: Location){
        locationsRef.document().set(location).await()
    }
}