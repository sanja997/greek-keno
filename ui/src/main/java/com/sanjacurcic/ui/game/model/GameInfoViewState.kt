package com.sanjacurcic.ui.game.model

import com.sanjacurcic.data.model.GameRoundModel

sealed class GameInfoViewState {
    class Result(val result: GameRoundModel) : GameInfoViewState()
    class Error(val error: Throwable) : GameInfoViewState()
    data object Loading : GameInfoViewState()
}