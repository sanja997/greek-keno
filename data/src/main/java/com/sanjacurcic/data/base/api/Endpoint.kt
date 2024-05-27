package com.sanjacurcic.data.base.api

object Endpoint {
    const val GET_GAME_ROUNDS = "/draws/v3.0/{gameId}/upcoming/20"
    const val GET_GAME_INFO = "/draws/v3.0/{gameId}/{drawId}"
    const val GAME_RESULTS = "/draws/v3.0/{gameId}/draw-date/{fromDate}/{toDate}"
}