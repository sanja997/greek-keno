package com.sanjacurcic.ui.gameresults.model

import com.sanjacurcic.domain.model.GameResultUiModel

sealed class GameResultsViewState {
    class Result(val result: List<GameResultUiModel>) : GameResultsViewState()
    class Error(val error: Throwable) : GameResultsViewState()
    data object Loading : GameResultsViewState()
}