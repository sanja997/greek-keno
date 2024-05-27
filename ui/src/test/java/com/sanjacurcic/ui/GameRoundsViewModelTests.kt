package com.sanjacurcic.ui

import com.sanjacurcic.data.base.functional.Either
import com.sanjacurcic.domain.usecase.GetGameRoundsUseCase
import com.sanjacurcic.ui.base.ViewModelBaseTest
import com.sanjacurcic.ui.gamerounds.GameRoundsViewModel
import com.sanjacurcic.ui.gamerounds.model.GameRoundsViewState
import com.sanjacurcic.ui.gamerounds.model.GameRoundsViewState.Error
import com.sanjacurcic.ui.gamerounds.model.GameRoundsViewState.Loading
import com.sanjacurcic.ui.gamerounds.model.GameRoundsViewState.Result
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
class GameRoundsViewModelTests : ViewModelBaseTest() {

    @InjectMocks
    lateinit var gameRoundsViewModel: GameRoundsViewModel

    @Mock
    private lateinit var gameRoundsUseCase: GetGameRoundsUseCase

    @Before
    fun setUp() {
        openMocks(this)
    }

    @Test
    fun `test getGameRoundsData, has result, state updated to Result`(): Unit = runTest {

        whenever(gameRoundsUseCase.invoke(anyInt())).thenReturn(Either.Result(MockData.gameRoundsList))

        val results = mutableListOf<GameRoundsViewState>()
        gameRoundsViewModel.state
            .onEach{ results.add(it)}
            .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

        gameRoundsViewModel.getRoundsData()
        testScheduler.runCurrent()

        assertTrue(results[0] is Loading)
        assertTrue(results[1] is Result)
    }

    @Test
    fun `test getGameRoundsData, has error, state updated to Error`(): Unit = runTest {

        whenever(gameRoundsUseCase.invoke(anyInt())).thenReturn(Either.Error(Throwable()))

        val results = mutableListOf<GameRoundsViewState>()
        gameRoundsViewModel.state
            .onEach{ results.add(it)}
            .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

        gameRoundsViewModel.getRoundsData()
        testScheduler.runCurrent()

        assertTrue(results[0] is Loading)
        assertTrue(results[1] is Error)
    }
}