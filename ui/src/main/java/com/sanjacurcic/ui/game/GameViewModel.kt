package com.sanjacurcic.ui.game

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanjacurcic.domain.usecase.GetGameInfoUseCase
import com.sanjacurcic.ui.game.model.GameInfoViewState
import com.sanjacurcic.ui.game.model.GameInfoViewState.Loading
import com.sanjacurcic.ui.game.model.GameInfoViewState.Result
import com.sanjacurcic.ui.game.model.GameInfoViewState.Error
import com.sanjacurcic.ui.gamerounds.view.GameRoundsViewHolder
import com.sanjacurcic.domain.helper.getFormattedTime
import com.sanjacurcic.ui.model.GameInfo.GREEK_KENO_GAME_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameInfoUseCase: GetGameInfoUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val drawId: Int? = savedStateHandle["drawId"]

    init {
        drawId?.let { getGameInfoData(it) }
    }

    val pickedNumbers = mutableListOf<Int>()
    var isGameEnded = false

    private val _state = MutableStateFlow<GameInfoViewState>(Loading)
    val state get() = _state.asStateFlow()

    fun getGameInfoData(drawId: Int) {

        viewModelScope.launch {
            val result = gameInfoUseCase(GREEK_KENO_GAME_ID, drawId)

            if (result.isResult) {
                _state.update { Result(result.result()) }
            } else {
                _state.update { Error(result.error()) }
            }
        }
    }

    fun convertEpochMilliToFormattedTime(timestamp: Long): String {
        val instantTime = Instant.ofEpochMilli(timestamp)
        return instantTime.getFormattedTime(GameRoundsViewHolder.TIME_PATTERN)
    }

    fun isNumberAlreadyPicked(number: Int) = pickedNumbers.contains(number)

    fun getTimeToPay(drawTime: Long): Long {
        return abs(Duration.between(Instant.ofEpochMilli(drawTime), Instant.now()).toMillis())
    }
}