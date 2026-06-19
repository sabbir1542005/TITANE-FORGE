package com.example.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Team(
    val id: String = "",
    val name: String = "",
    val tag: String = "",
    val game: String = "",
    val logoUrl: String = "",
    val wins: Int = 0,
    val losses: Int = 0,
    val earnings: Double = 0.0,
    val region: String = "Global"
)
