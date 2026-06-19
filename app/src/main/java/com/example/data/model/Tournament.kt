package com.example.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tournament(
    val id: String = "",
    val name: String = "",
    val game: String = "",
    val prizePool: Double = 0.0,
    val startDate: String = "",
    val status: String = "Upcoming", // "Upcoming", "Live", "Completed"
    val maxTeams: Int = 16,
    val matchesPlayed: Int = 0
)
