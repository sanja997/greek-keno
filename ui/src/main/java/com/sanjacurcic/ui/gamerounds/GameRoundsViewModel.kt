package com.sanjacurcic.ui.gamerounds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanjacurcic.domain.usecase.GetGameRoundsUseCase
import com.sanjacurcic.ui.model.GameInfo.GREEK_KENO_GAME_ID
import com.sanjacurcic.ui.gamerounds.model.GameRoundsViewState
import com.sanjacurcic.ui.gamerounds.model.GameRoundsViewState.Loading
import com.sanjacurcic.ui.gamerounds.model.GameRoundsViewState.Result
import com.sanjacurcic.ui.gamerounds.model.GameRoundsViewState.Error
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameRoundsViewModel @Inject constructor(
    private val getGameRoundsUseCase: GetGameRoundsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<GameRoundsViewState>(Loading)
    val state get() = _state.asStateFlow()

    fun getRoundsData() {

        viewModelScope.launch {
            val result = getGameRoundsUseCase(GREEK_KENO_GAME_ID)

            if (result.isResult) {
                _state.update { Result(result.result()) }
            } else {
                _state.update { Error(result.error()) }
            }
        }
    }
}