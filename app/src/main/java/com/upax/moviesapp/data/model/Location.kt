package com.upax.moviesapp.data.model

import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Location(
    @ServerTimestamp
    val created_at: Date? = null,
    val position: GeoPoint? = null
)

