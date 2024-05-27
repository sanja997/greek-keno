package com.sanjacurcic.domain.model

data class GameResultUiModel(
    val drawTime: Long,
    val drawId: Int,
    val winningNumbers: List<Int>
)