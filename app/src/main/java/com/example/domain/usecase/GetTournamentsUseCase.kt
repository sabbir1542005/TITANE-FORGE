package com.example.domain.usecase

import com.example.data.model.Tournament
import com.example.domain.repository.EsportsRepository
import kotlinx.coroutines.flow.Flow

class GetTournamentsUseCase(private val repository: EsportsRepository) {
    operator fun invoke(): Flow<List<Tournament>> = repository.getTournaments()
}
