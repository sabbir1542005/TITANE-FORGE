package com.example.domain.repository

import com.example.data.model.Player
import com.example.data.model.Team
import com.example.data.model.Tournament
import kotlinx.coroutines.flow.Flow

interface EsportsRepository {
    fun getTeams(): Flow<List<Team>>
    fun getPlayersByTeam(teamId: String): Flow<List<Player>>
    fun getTournaments(): Flow<List<Tournament>>
    suspend fun createTeam(team: Team): Result<Unit>
    suspend fun registerPlayer(player: Player): Result<Unit>
    suspend fun addTournament(tournament: Tournament): Result<Unit>
}
