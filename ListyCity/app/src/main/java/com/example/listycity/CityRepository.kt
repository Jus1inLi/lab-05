package com.example.listycity

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class CityRepository {


    private val _cities = mutableStateListOf(
        City("Edmonton", "AB"),
        City("Vancouver", "BC"),
        City("Toronto", "ON")
    )

    private val db = Firebase.firestore
    private val citiesRef = db.collection("cities")

    init {
        citiesRef.addSnapshotListener { snapshot, error ->
            if (error != null) {

                return@addSnapshotListener
            }


            _cities.clear()
            snapshot?.documents?.forEach { document ->
                document.toObject(City::class.java)?.let { _cities.add(it) }
            }
        }
    }

    val cities: List<City>
        get() = _cities

    fun addCity(city: City) {
        citiesRef.add(city)

    }

    fun updateCity(oldCity: City, updatedCity: City) {
        if (oldCity.id.isEmpty()) return
        citiesRef.document(oldCity.id).set(updatedCity)

    }

    fun deleteCity(city: City) {
        if (city.id.isEmpty()) return
        citiesRef.document(city.id).delete()


    }
}