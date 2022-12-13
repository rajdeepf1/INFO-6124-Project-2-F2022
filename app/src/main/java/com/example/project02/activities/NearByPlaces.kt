package com.example.project02.activities

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project02.databinding.ActivityNearByPlacesBinding
import com.example.project02.interfaces.IGoogleAPIService
import com.example.project02.models.MyPlaces
import com.example.project02.models.NearByPlacesDataModel
import com.example.project02.utils.Common
import com.rajdeepsingh.birthdayreminderapp.adapters.NearByPlacesAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class NearByPlaces : AppCompatActivity() {

    private lateinit var binding: ActivityNearByPlacesBinding

    lateinit var mService: IGoogleAPIService

    lateinit var currentPlace: MyPlaces

    lateinit var list: ArrayList<NearByPlacesDataModel>

    var geocoder: Geocoder? = null

    var adapter: NearByPlacesAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNearByPlacesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mService = Common.googleApiService
        geocoder = Geocoder(this, Locale.getDefault())
        list = ArrayList()
        val intent = getIntent()
        val latitude: Double? = intent.getDoubleExtra("CURR_LAT",0.0)
        val longitude:Double? = intent.getDoubleExtra("CURR_LNG",0.0)

        nearByPlace(latitude!!,longitude!!,"any")

    }
// https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=AIzaSyCGFPkQsxd7X4L4KIH5UQL_PrHXUvVjghE
    private fun getUrl (latitude: Double, longitude: Double, typePlace: String) : String {
        val googlePlaceUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        googlePlaceUrl.append("?location=$latitude,$longitude")
        googlePlaceUrl.append ("&radius=10000") // 10km
        googlePlaceUrl.append ("&type$typePlace")
        googlePlaceUrl. append ("&key=AIzaSyCGFPkQsxd7X4L4KIH5UQL_PrHXUvVjghE")
        Log.d("URL_DEBUG", googlePlaceUrl.toString())
        return googlePlaceUrl.toString()
    }

    private fun nearByPlace (latitude: Double, longitude: Double,typePlace: String) {
        val url = getUrl(latitude, longitude, typePlace)
        mService.getNearbyPlaces(url)
            . enqueue (object : Callback<MyPlaces> {
                override fun onResponse(call: Call<MyPlaces>?, response: Response<MyPlaces>?){

                    currentPlace = response?.body()!!

                    if (response!!.isSuccessful){
                        for (i in 0 until response!!.body ()!!.results!!.size){
                            val googlePlace = response.body () !!.results!! [i]
                            val lat = googlePlace.geometry!!.location!!.lat
                            val lng = googlePlace.geometry!!.location!!.lng
                            val placeName = googlePlace.name
                            //val latIng = LatLng (lat, Ing)

                            val address: List<Address> = geocoder?.getFromLocation(lat, lng, 1)!!
                            list.add(NearByPlacesDataModel(placeName.toString(),address[0]!!.getAddressLine(0)))

                            adapter = NearByPlacesAdapter(applicationContext,list)
                            // Setting the Adapter with the recyclerview
                            binding.recyclerView.adapter = adapter
                            // this creates a vertical layout Manager
                            binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)

                        }
                    }

                }
                override fun onFailure(call: Call<MyPlaces>?, t: Throwable?) {
                    Toast.makeText (applicationContext, ""+t?.message, Toast.LENGTH_SHORT) . show ()
            }
    })
}

}