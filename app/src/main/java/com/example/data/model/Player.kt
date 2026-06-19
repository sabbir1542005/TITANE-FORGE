package com.example.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Player(
    val id: String = "",
    val gamerTag: String = "",
    val realName: String = "",
    val role: String = "",
    val teamId: String? = null,
    val imageUrl: String = "",
    val nationality: String = "",
    val isCaptain: Boolean = false,
    val contractExpiryDate: String = ""
)
