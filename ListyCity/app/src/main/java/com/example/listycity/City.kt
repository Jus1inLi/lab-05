package com.example.listycity

import com.google.firebase.firestore.DocumentId

data class City(
    @DocumentId val id: String = "",
    val name: String = "",
    val province: String = ""
)