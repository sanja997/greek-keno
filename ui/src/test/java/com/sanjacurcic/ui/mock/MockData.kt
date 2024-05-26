package com.sanjacurcic.ui.mock

import com.sanjacurcic.data.model.GameRoundModel
import com.sanjacurcic.data.model.PricePointsModel

object MockData {
    val gameRoundModel = GameRoundModel(
        123, 123, 123L, "", PricePointsModel(
            emptyList(),
            1.0
        ), emptyList()
    )

    val gameRoundsList = listOf(gameRoundModel, gameRoundModel, gameRoundModel)
}