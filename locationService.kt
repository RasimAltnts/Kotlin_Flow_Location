package com.example.permissionlocation.service

import android.annotation.SuppressLint
import android.app.Activity
import androidx.lifecycle.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow


class locationService(private val activity:Activity) : ViewModel() {
    val long: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }
    val lat: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }



    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
    val lastLocation= flow<Any> {
        while(true){
            getLastLocation()
            delay(10000L)
        }
    }

    val currentLocation = flow<Any> {
        while(true){
            currentLocation()
            delay(10000L)
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun getLastLocation(){
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if(it==null){
                println("Last Location is null")
            }
            else it.apply {
                lat.value = it.latitude
                long.value = it.longitude
                println("lat:${it.latitude},lon:${it.longitude}")
            }
        }
    }
    @SuppressLint("MissingPermission")
    suspend fun currentLocation(){
        fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY,null)
        getLastLocation()

    }

}