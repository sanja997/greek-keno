package com.sanjacurcic.ui

import com.sanjacurcic.data.base.functional.Either
import com.sanjacurcic.domain.usecase.GetGameResultUseCase
import com.sanjacurcic.ui.base.ViewModelBaseTest
import com.sanjacurcic.ui.gameresults.GameResultsViewModel
import com.sanjacurcic.ui.gameresults.model.GameResultsViewState
import com.sanjacurcic.ui.gameresults.model.GameResultsViewState.Error
import com.sanjacurcic.ui.gameresults.model.GameResultsViewState.Loading
import com.sanjacurcic.ui.gameresults.model.GameResultsViewState.Result
import com.sanjacurcic.ui.mock.MockData
import junit.framework.TestCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GameResultsViewModelTests : ViewModelBaseTest() {

    @InjectMocks
    lateinit var gameResultsViewModel: GameResultsViewModel

    @Mock
    private lateinit var gameResultsUseCase: GetGameResultUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test getGameResultsData, has result, state updated to Result`(): Unit = runTest {

        whenever(gameResultsUseCase.invoke(ArgumentMatchers.anyInt())).thenReturn(Either.Result(MockData.gameResultsList))

        val results = mutableListOf<GameResultsViewState>()
        gameResultsViewModel.state
            .onEach{ results.add(it)}
            .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

        gameResultsViewModel.getGameResults()
        testScheduler.runCurrent()

        TestCase.assertTrue(results[0] is Loading)
        TestCase.assertTrue(results[1] is Result)
    }

    @Test
    fun `test getGameResultsData, has error, state updated to Error`(): Unit = runTest {

        whenever(gameResultsUseCase.invoke(ArgumentMatchers.anyInt())).thenReturn(Either.Error(Throwable()))

        val results = mutableListOf<GameResultsViewState>()
        gameResultsViewModel.state
            .onEach{ results.add(it)}
            .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

        gameResultsViewModel.getGameResults()
        testScheduler.runCurrent()

        TestCase.assertTrue(results[0] is Loading)
        TestCase.assertTrue(results[1] is Error)
    }
}