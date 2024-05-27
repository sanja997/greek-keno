package com.sanjacurcic.ui.gameresults

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanjacurcic.domain.usecase.GetGameResultUseCase
import com.sanjacurcic.ui.gameresults.model.GameResultsViewState
import com.sanjacurcic.ui.gameresults.model.GameResultsViewState.Error
import com.sanjacurcic.ui.gameresults.model.GameResultsViewState.Result
import com.sanjacurcic.ui.model.GameInfo.GREEK_KENO_GAME_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameResultsViewModel @Inject constructor(
    private val getGameResultUseCase: GetGameResultUseCase
)  : ViewModel() {

    private val _state = MutableStateFlow<GameResultsViewState>(GameResultsViewState.Loading)
    val state get() = _state.asStateFlow()

    fun getGameResults() {

        viewModelScope.launch {
            val result = getGameResultUseCase(GREEK_KENO_GAME_ID)

            if (result.isResult) {
                _state.update { Result(result.result()) }
            } else {
                _state.update { Error(result.error()) }
            }
        }
    }
}