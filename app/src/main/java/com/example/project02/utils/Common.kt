package com.example.project02.utils

import com.example.project02.interfaces.IGoogleAPIService

object Common {
    private val GOOGLE_API_URL="https://maps.googleapis.com/"
    val googleApiService: IGoogleAPIService
        get()=RetrofitClient.getClient (GOOGLE_API_URL).create(IGoogleAPIService:: class. java)
}