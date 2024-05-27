package com.sanjacurcic.data.model

data class GameRoundModel(
    val gameId: Int,
    val drawId: Int,
    val drawTime: Long,
    val status: String,
    val pricePoints: PricePointsModel,
    val prizeCategories: List<PriceCategoryModel>,
    val winningNumbers: WinningNumbersModel
)