package com.sanjacurcic.ui.gamerounds.model

import com.sanjacurcic.data.model.GameRoundModel

sealed class GameRoundsViewState {
    class Result(val result: List<GameRoundModel>) : GameRoundsViewState()
    class Error(val error: Throwable) : GameRoundsViewState()
    data object Loading : GameRoundsViewState()
}