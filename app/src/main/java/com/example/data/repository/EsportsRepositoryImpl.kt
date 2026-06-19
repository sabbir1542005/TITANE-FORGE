package com.example.data.repository

import com.example.data.model.Player
import com.example.data.model.Team
import com.example.data.model.Tournament
import com.example.domain.repository.EsportsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class EsportsRepositoryImpl : EsportsRepository {

    private val teamsState = MutableStateFlow(
        listOf(
            Team("t1", "TITANE FORGE RIVAL", "TFR", "Valorant", "", 24, 6, 120000.0, "North America"),
            Team("t2", "NEXUS ALPHA", "NXA", "League of Legends", "", 18, 9, 85000.0, "Europe"),
            Team("t3", "VOID SHADOWS", "VDS", "Counter-Strike 2", "", 31, 4, 250000.0, "Asia Pacific"),
            Team("t4", "APEX ECLIPSE", "APE", "Apex Legends", "", 15, 12, 45000.0, "South America")
        )
    )

    private val playersState = MutableStateFlow(
        listOf(
            Player("p1", "Valkyrie", "Emma Stone", "IGL / Duelist", "t1", "", "USA", true, "2027-12-01"),
            Player("p2", "Spector", "Liam Harris", "Sentinel", "t1", "", "Canada", false, "2026-11-15"),
            Player("p3", "Kaelen", "Kaelen Mercer", "Initiator", "t1", "", "United Kingdom", false, "2026-08-30"),
            Player("p4", "Jinxed", "Chloe Vance", "Controller", "t1", "", "France", false, "2027-01-10"),
            Player("p5", "Shifter", "Kenji Takahashi", "Flex", "t1", "", "Japan", false, "2028-05-01"),
            
            Player("p6", "FakerJr", "Jin Woo", "Mid Laner", "t2", "", "South Korea", true, "2028-11-20"),
            Player("p7", "Odin", "Gunnar Nielsen", "Jungler", "t2", "", "Denmark", false, "2026-12-15")
        )
    )

    private val tournamentsState = MutableStateFlow(
        listOf(
            Tournament("tr1", "Titan Forge Invitational 2026", "Valorant", 50000.0, "2026-07-10", "Upcoming", 16),
            Tournament("tr2", "Galaxy Cyber Cup", "Counter-Strike 2", 150000.0, "2026-06-25", "Live", 8, 12),
            Tournament("tr3", "Global Apex Showdown", "Apex Legends", 30000.0, "2026-05-18", "Completed", 20, 20)
        )
    )

    override fun getTeams(): Flow<List<Team>> = teamsState

    override fun getPlayersByTeam(teamId: String): Flow<List<Player>> {
        return playersState.map { players -> players.filter { it.teamId == teamId } }
    }

    override fun getTournaments(): Flow<List<Tournament>> = tournamentsState

    override suspend fun createTeam(team: Team): Result<Unit> {
        val current = teamsState.value.toMutableList()
        current.add(team.copy(id = "team_" + System.currentTimeMillis()))
        teamsState.value = current
        return Result.success(Unit)
    }

    override suspend fun registerPlayer(player: Player): Result<Unit> {
        val current = playersState.value.toMutableList()
        current.add(player.copy(id = "player_" + System.currentTimeMillis()))
        playersState.value = current
        return Result.success(Unit)
    }

    override suspend fun addTournament(tournament: Tournament): Result<Unit> {
        val current = tournamentsState.value.toMutableList()
        current.add(tournament.copy(id = "tourney_" + System.currentTimeMillis()))
        tournamentsState.value = current
        return Result.success(Unit)
    }
}
// Singleton container to emulate dependency injection (Constructor Injection/Service Locator style)
object EsportsDependencyRegistry {
    val repository: EsportsRepository by lazy { EsportsRepositoryImpl() }
}
