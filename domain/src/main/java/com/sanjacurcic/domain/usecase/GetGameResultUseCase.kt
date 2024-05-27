package com.sanjacurcic.domain.usecase

import com.sanjacurcic.data.base.functional.Either
import com.sanjacurcic.data.repository.GameRepository
import com.sanjacurcic.domain.helper.getFormattedDate
import com.sanjacurcic.domain.model.GameResultUiModel
import java.time.Instant
import javax.inject.Inject

class GetGameResultUseCase @Inject constructor(
    private val repository: GameRepository
) {

    companion object {
        const val DATE_PATTERN = "YYYY-MM-dd"
    }

    suspend operator fun invoke(gameId: Int): Either<Throwable, List<GameResultUiModel>> {

        val date = Instant.now().getFormattedDate(DATE_PATTERN)
        val result = repository.getGameResult(gameId, date, date)

        val resultUiModel = result.takeIf { it is Either.Result }?.result().let {
            it?.content?.map { gameModel ->
                GameResultUiModel(
                    gameModel.drawTime,
                    gameModel.drawId,
                    gameModel.winningNumbers.list
                )
            }
        }

        if (resultUiModel != null) return Either.Result(resultUiModel)
        return Either.Error(result.error())
    }
}