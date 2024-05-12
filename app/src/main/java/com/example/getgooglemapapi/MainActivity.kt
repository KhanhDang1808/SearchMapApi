package com.example.getgooglemapapi

import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class MainActivity : AppCompatActivity() {
    //AIzaSyB0LN5rDvj5VyfeK56KFNkGRz1U_Ne_XVM
    private val apiKey = "AIzaSyB0LN5rDvj5VyfeK56KFNkGRz1U_Ne_XVM"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiKey = getString(R.string.YOUR_API_KEY_HERE)
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }
        // Create a new Places client instance.
        val placesClient = Places.createClient(this)
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as AutocompleteSupportFragment?
        autocompleteFragment!!.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
            )
        )


        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                val address = place.address?.toString()
                if (address != null) {
                    getDirection(address)
                }
            }

            override fun onError(status: Status) {
                Log.e("error", status.toString())
                Toast.makeText(this@MainActivity, "Error: ${status.toString()}", Toast.LENGTH_SHORT)
                    .show()
            }
        })


//                                val placesClient2 = Places.createClient(this)
//
//                        // Get the text from the AutocompleteSupportFragment
//                                val query = "ha noi"
//
//                        // Create a FindAutocompletePredictionsRequest object
//                                val request = FindAutocompletePredictionsRequest.builder()
//                                    .setQuery(query)
//                                    .build()
//
//                        // Use PlacesClient to find autocomplete predictions
//                                placesClient2.findAutocompletePredictions(request)
//                                    .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
//                                        for (prediction in response.autocompletePredictions) {
//                                            Log.i("khanh", "Place prediction: ${prediction.getFullText(null)}")
//                                        }
//                                    }
//                                    .addOnFailureListener { exception: Exception ->
//                                        if (exception is ApiException) {
//                                            Log.e("khanh", "Place not found: ${exception.statusCode}")
//                                        } else {
//                                            Log.e("khanh", "Unexpected error: ${exception.localizedMessage}")
//                                        }
//                                    }
    }

    private fun getDirection(to: String) {
        try {
//            val uri: Uri =Uri.parse("https://www.google.com/maps/dir/$from/$to")
//            val intent = Intent(Intent.ACTION_VIEW,uri)
            val uri = "google.navigation:q=$to"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setPackage("com.google.android.apps.maps")
            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (exception: ActivityNotFoundException) {
            val uri: Uri =
                Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

}