package com.sanjacurcic.data.dto

data class GameRoundResponse(
    val gameId: Int?,
    val drawId: Int?,
    val drawTime: Long?,
    val status: String?,
    val pricePoints: PricePointsResponse?,
    val prizeCategories: List<PriceCategoryResponse>?
)