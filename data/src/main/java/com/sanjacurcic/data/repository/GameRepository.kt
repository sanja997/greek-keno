package com.sanjacurcic.data.repository

import com.sanjacurcic.data.base.functional.Either
import com.sanjacurcic.data.datasource.DataSource
import com.sanjacurcic.data.mapper.GameResultsMapper
import com.sanjacurcic.data.mapper.GameRoundsMapper
import com.sanjacurcic.data.model.GameResultsModel
import com.sanjacurcic.data.model.GameRoundModel
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val dataSource: DataSource
) {

    suspend fun getGameRounds(gameId: Int): Either<Throwable, List<GameRoundModel>> {
        val response = dataSource.getGameRounds(gameId)

        return if (response.isResult) {
            Either.Result(
                response.result().map {
                    GameRoundsMapper().map(it)
                }
            )
        } else Either.Error(response.error())
    }

    suspend fun getGameInfo(gameId: Int, drawId: Int): Either<Throwable, GameRoundModel> {
        val response = dataSource.getGameInfo(gameId, drawId)

        return if (response.isResult) {
            Either.Result(GameRoundsMapper().map(response.result()))
        } else Either.Error(response.error())
    }

    suspend fun getGameResult(gameId: Int, fromDate: String, toDate: String): Either<Throwable, GameResultsModel> {
        val response = dataSource.getGameResults(gameId, fromDate, toDate)

        return if (response.isResult) {
            Either.Result(GameResultsMapper().map(response.result()))
        } else Either.Error(response.error())
    }
}