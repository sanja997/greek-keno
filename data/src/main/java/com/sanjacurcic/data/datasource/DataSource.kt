package com.sanjacurcic.data.datasource

import com.sanjacurcic.data.base.functional.Either
import com.sanjacurcic.data.api.ApiService
import com.sanjacurcic.data.base.functional.RemoteDataSource
import com.sanjacurcic.data.dto.GameRoundResponse
import javax.inject.Inject

class DataSource @Inject constructor(
    private val apiService: ApiService
): RemoteDataSource() {
    suspend fun getGameRounds(gameId: Int): Either<Throwable, List<GameRoundResponse>> {
        return getResult { apiService.getGameRounds(gameId) }
    }

    suspend fun getGameInfo(gameId: Int, drawId: Int): Either<Throwable, GameRoundResponse> {
        return getResult { apiService.getGameInfo(gameId, drawId) }
    }
}