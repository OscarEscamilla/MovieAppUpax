package com.upax.moviesapp.repository


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.inject.Deferred
import com.upax.moviesapp.data.model.Location
import javax.inject.Inject

class LocationRepository @Inject constructor(private val locationsRef: CollectionReference) {


    fun getAllLocations(): List<Location> {
        val listLocation = mutableListOf<Location>()
        locationsRef.get().addOnSuccessListener { result ->
            for (document in result){
                val created_at = document.getDate("created_at")
                val position = document.getGeoPoint("position")
                val location = Location(created_at, position)
                listLocation.add(location)
            }
            Log.e("locaa","${listLocation}")
        }
        Log.e("loca","${listLocation}")
        return listLocation.toList()
    }


    fun saveLocation(location: Location) {
        locationsRef.document().set(location).addOnSuccessListener {
            return@addOnSuccessListener
        }
    }

}
