package com.example.nacho.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.location.LocationListener
import android.location.LocationManager
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.support.design.widget.FloatingActionButton

import com.google.android.gms.maps.CameraUpdateFactory




class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var locationManager: LocationManager? = null
    var PLACE_PICKER_REQUEST = 1
    // private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        const val code: Int = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val PLACE_PICKER_REQUEST = 1
            val builder = PlacePicker.IntentBuilder()

            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST)
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val home = LatLng(40.647988, -3.981343)

       mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.addMarker(MarkerOptions().position(home).title("Marker in Home"))
        mMap.setMinZoomPreference(10F)
        mMap.setMaxZoomPreference(20F)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(home))
        mMap.uiSettings.isZoomControlsEnabled = true

    }






}
