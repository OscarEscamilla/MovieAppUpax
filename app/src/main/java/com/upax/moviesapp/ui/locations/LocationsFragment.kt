package com.upax.moviesapp.ui.locations

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.FirebaseFirestore
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.upax.moviesapp.R
import com.upax.moviesapp.databinding.FragmentLocationsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LocationsFragment : Fragment() {

    private lateinit var binding: FragmentLocationsBinding
    private val viewModel: LocationsViewModel by viewModels()
    private var mapView: MapView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding.mapView
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)
        //setupObservers()
        getLocations()
    }

    private fun getLocations(){
        val locationsRef = FirebaseFirestore.getInstance().collection("locations")
        locationsRef.get().addOnSuccessListener { result ->
            val positionCamera = result.documents.last().getGeoPoint("position")
            if (positionCamera != null) {
                updateCamera(positionCamera.longitude, positionCamera.latitude)
            }
            for (document in result){
                val created_at = document.getDate("created_at")
                val position = document.getGeoPoint("position")
                if (position != null) {
                    addMarker(position.longitude, position.latitude, created_at!!)
                }
            }
        }
    }

    private fun addMarker(longitude: Double, latitude: Double, date: Date) {
        try {
            bitmapFromDrawableRes(
                activity!!,
                R.drawable.red_marker
            )?.let {
                val annotationApi = mapView?.annotations
                val pointAnnotationManager = mapView?.let { annotationApi?.createPointAnnotationManager() }
                val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                    .withPoint(Point.fromLngLat(longitude, latitude))
                    .withIconImage(it)
                    .withTextField(date.toString())
                    .withTextSize(10.0)
                pointAnnotationManager?.create(pointAnnotationOptions)
            }
        }catch (e:Exception){
            Log.e("Marker",e.message.toString())
        }
    }

    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
            // copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }

    private fun updateCamera(longitude: Double, latitude: Double) {
        val mapAnimationOptions = MapAnimationOptions.Builder().duration(1500L).build()
        mapView?.camera?.easeTo(
            CameraOptions.Builder()
                // Centers the camera to the lng/lat specified.
                .center(Point.fromLngLat(longitude, latitude))
                // specifies the zoom value. Increase or decrease to zoom in or zoom out
                .zoom(14.0)
                // specify frame of reference from the center.
                .padding(EdgeInsets(500.0, 0.0, 0.0, 0.0))
                .build(),
            mapAnimationOptions
        )
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }


}