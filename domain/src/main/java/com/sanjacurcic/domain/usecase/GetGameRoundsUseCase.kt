package com.sanjacurcic.domain.usecase

import Either
import com.sanjacurcic.data.model.GameRoundModel
import com.sanjacurcic.data.repository.GameRepository
import javax.inject.Inject

class GetGameRoundsUseCase @Inject constructor(
    private val repository: GameRepository
) {

    suspend operator fun invoke(gameId: Int): Either<Throwable, List<GameRoundModel>> {
        return repository.getGameRounds(gameId)
    }
}