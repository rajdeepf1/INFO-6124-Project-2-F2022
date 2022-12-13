package com.example.project02.interfaces

import com.example.project02.models.MyPlaces
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface IGoogleAPIService {
    @GET
    fun getNearbyPlaces (@Url url:String) : Call<MyPlaces>
}