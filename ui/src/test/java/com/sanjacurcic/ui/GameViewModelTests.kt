package com.sanjacurcic.ui

import com.sanjacurcic.data.base.functional.Either
import com.sanjacurcic.domain.usecase.GetGameInfoUseCase
import com.sanjacurcic.ui.base.ViewModelBaseTest
import com.sanjacurcic.ui.game.GameViewModel
import com.sanjacurcic.ui.game.model.GameInfoViewState
import com.sanjacurcic.ui.game.model.GameInfoViewState.Error
import com.sanjacurcic.ui.game.model.GameInfoViewState.Loading
import com.sanjacurcic.ui.game.model.GameInfoViewState.Result
import com.sanjacurcic.ui.mock.MockData
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTests : ViewModelBaseTest() {

    @InjectMocks
    lateinit var gameViewModel: GameViewModel

    @Mock
    private lateinit var gameInfoUseCase: GetGameInfoUseCase

    @Before
    fun setUp() {
        openMocks(this)
    }

    @Test
    fun `test getGameData, has result, state updated to Result`(): Unit = runTest {

        whenever(gameInfoUseCase.invoke(anyInt(), anyInt())).thenReturn(Either.Result(MockData.gameRoundModel))

        val results = mutableListOf<GameInfoViewState>()
        gameViewModel.state
            .onEach{ results.add(it)}
            .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

        gameViewModel.getGameInfoData(123)
        testScheduler.runCurrent()

        assertTrue(results[0] is Loading)
        assertTrue(results[1] is Result)
    }

    @Test
    fun `test getGameData, has error, state updated to Error`(): Unit = runTest {

        whenever(gameInfoUseCase.invoke(anyInt(), anyInt())).thenReturn(Either.Error(Throwable()))

        val results = mutableListOf<GameInfoViewState>()
        gameViewModel.state
            .onEach{ results.add(it)}
            .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

        gameViewModel.getGameInfoData(123)
        testScheduler.runCurrent()

        assertTrue(results[0] is Loading)
        assertTrue(results[1] is Error)
    }
}