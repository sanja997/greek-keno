package com.sanjacurcic.data.mapper

import com.sanjacurcic.data.base.functional.IMapper
import com.sanjacurcic.data.dto.GameResultsResponse
import com.sanjacurcic.data.model.GameResultsModel

class GameResultsMapper : IMapper<GameResultsResponse, GameResultsModel> {
    override fun map(from: GameResultsResponse?): GameResultsModel {
        return GameResultsModel(
            content = from?.content?.map { GameRoundsMapper().map(it) } ?: emptyList()
        )
    }
}