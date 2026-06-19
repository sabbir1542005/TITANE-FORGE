package com.example.domain.usecase

import com.example.data.model.Team
import com.example.domain.repository.EsportsRepository
import kotlinx.coroutines.flow.Flow

class GetTeamsUseCase(private val repository: EsportsRepository) {
    operator fun invoke(): Flow<List<Team>> = repository.getTeams()
}
