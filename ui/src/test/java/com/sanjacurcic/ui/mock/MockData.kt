package com.sanjacurcic.ui.mock

import com.sanjacurcic.data.model.GameRoundModel
import com.sanjacurcic.data.model.PricePointsModel
import com.sanjacurcic.data.model.WinningNumbersModel
import com.sanjacurcic.domain.model.GameResultUiModel

object MockData {
    val winningNumbers = WinningNumbersModel(listOf(1, 2, 3))

    val gameRoundModel = GameRoundModel(
        123, 123, 123L, "", PricePointsModel(
            emptyList(),
            1.0
        ), emptyList(), winningNumbers
    )

    val gameRoundsList = listOf(gameRoundModel, gameRoundModel, gameRoundModel)

    val gameResultsList = listOf(GameResultUiModel(123, 123, listOf(1, 2, 3)))
}