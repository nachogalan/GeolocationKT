package com.example.nacho.myapplication


import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import android.location.LocationManager
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.ui.PlacePicker

import com.google.android.gms.maps.CameraUpdateFactory
import android.widget.Toast
import com.google.android.gms.location.places.Place
import android.content.Intent
import kotlinx.android.synthetic.main.activity_maps.*
import android.animation.Animator
import android.app.PendingIntent.getActivity
import android.util.Log


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var currentLocation: Location

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {


        const val PLACE_PICKER_REQUEST = 1
        const val REQUEST_LOCATION_PERMISSION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        animation_view.setAnimation("loader.json")
        animation_view.playAnimation()
        animation_view.speed

        animation_view.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                Log.e("Animation:", "start")
            }

            override fun onAnimationEnd(animation: Animator) {
                Log.e("Animation:", "end")


            }

            override fun onAnimationCancel(animation: Animator) {
                Log.e("Animation:", "cancel")
            }

            override fun onAnimationRepeat(animation: Animator) {
                Log.e("Animation:", "repeat")
               // animation_view.visibility = View.GONE
                animation_view.cancelAnimation()
            }
        })


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val home = LatLng(40.647988, -3.981343)



        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            val permissions = arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION)
            ActivityCompat.requestPermissions(this, permissions, MapsActivity.REQUEST_LOCATION_PERMISSION)
            mMap.setMyLocationEnabled(true)
        }
        else {
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            mMap.addMarker(MarkerOptions().position(home).title("Marker in Home"))
            mMap.setMinZoomPreference(10F)
            mMap.setMaxZoomPreference(20F)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(home))
            mMap.uiSettings.isZoomControlsEnabled = true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(data!!, this)
                val toastMsg = String.format("Place: %s", place.name)
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.latLng))
                mMap.addMarker(MarkerOptions().position(place.latLng).title("New marker"))

            }
        }
    }

    fun showPlacePicker(view: View){
        val builder = PlacePicker.IntentBuilder()

        startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST)
    }



}
