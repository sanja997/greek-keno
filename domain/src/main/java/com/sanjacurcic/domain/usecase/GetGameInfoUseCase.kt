package com.sanjacurcic.domain.usecase

import Either
import com.sanjacurcic.data.model.GameRoundModel
import com.sanjacurcic.data.repository.GameRepository
import javax.inject.Inject

class GetGameInfoUseCase @Inject constructor(
    private val repository: GameRepository
) {

    suspend operator fun invoke(gameId: Int, drawId: Int): Either<Throwable, GameRoundModel> {
        return repository.getGameInfo(gameId, drawId)
    }
}