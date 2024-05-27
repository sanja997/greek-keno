package com.sanjacurcic.data.mapper

import com.sanjacurcic.data.base.functional.IMapper
import com.sanjacurcic.data.dto.GameRoundResponse
import com.sanjacurcic.data.model.GameRoundModel

class GameRoundsMapper : IMapper<GameRoundResponse, GameRoundModel> {
    override fun map(from: GameRoundResponse?): GameRoundModel {
        return GameRoundModel(
            gameId = from?.gameId ?: 0,
            drawId = from?.drawId ?: 0,
            drawTime = from?.drawTime ?: 0,
            status = from?.status.orEmpty(),
            pricePoints = PricePointsMapper().map(from?.pricePoints),
            prizeCategories = from?.prizeCategories?.map { PrizeCategoryMapper().map(it) } ?: emptyList(),
            winningNumbers = WinningNumbersMapper().map(from?.winningNumbers)
        )
    }
}