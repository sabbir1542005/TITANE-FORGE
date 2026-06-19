package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Player
import com.example.data.model.Team
import com.example.data.model.Tournament
import com.example.domain.repository.EsportsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed interface DashboardUiState {
    object Loading : DashboardUiState
    data class Success(
        val teams: List<Team>,
        val tournaments: List<Tournament>
    ) : DashboardUiState
    data class Error(val message: String) : DashboardUiState
}

class ForgeDashboardViewModel(
    private val repository: EsportsRepository
) : ViewModel() {

    // Mock Authentication Guard Mode
    private val _userAuthenticated = MutableStateFlow(true)
    val userAuthenticated: StateFlow<Boolean> = _userAuthenticated.asStateFlow()

    val uiState: StateFlow<DashboardUiState> = combine(
        repository.getTeams(),
        repository.getTournaments()
    ) { teams, tournaments ->
        if (teams.isEmpty() && tournaments.isEmpty()) {
            DashboardUiState.Loading
        } else {
            DashboardUiState.Success(teams, tournaments)
        }
    }.catch { e ->
        emit(DashboardUiState.Error(e.localizedMessage ?: "Unknown compilation exception"))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardUiState.Loading
    )

    // Current selected team for details panel
    private val _selectedTeamId = MutableStateFlow<String?>("t1")
    val selectedTeamId: StateFlow<String?> = _selectedTeamId.asStateFlow()

    val selectedTeamPlayers: StateFlow<List<Player>> = _selectedTeamId
        .flatMapLatest { id ->
            if (id != null) {
                repository.getPlayersByTeam(id)
            } else {
                flowOf(emptyList())
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun selectTeam(teamId: String) {
        _selectedTeamId.value = teamId
    }

    fun toggleAuth() {
        _userAuthenticated.value = !_userAuthenticated.value
    }

    fun addTeam(name: String, tag: String, game: String, region: String) {
        viewModelScope.launch {
            if (_userAuthenticated.value) {
                repository.createTeam(Team(name = name, tag = tag, game = game, region = region))
            }
        }
    }

    fun addTournament(name: String, game: String, prizePool: Double) {
        viewModelScope.launch {
            if (_userAuthenticated.value) {
                repository.addTournament(Tournament(name = name, game = game, prizePool = prizePool))
            }
        }
    }
}
