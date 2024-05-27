package com.sanjacurcic.data.api

import com.sanjacurcic.data.base.api.Endpoint.GAME_RESULTS
import com.sanjacurcic.data.base.api.Endpoint.GET_GAME_INFO
import com.sanjacurcic.data.base.api.Endpoint.GET_GAME_ROUNDS
import com.sanjacurcic.data.dto.GameResultsResponse
import com.sanjacurcic.data.dto.GameRoundResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(GET_GAME_ROUNDS)
    suspend fun getGameRounds(
        @Path("gameId") gameId: Int
    ): Response<List<GameRoundResponse>>

    @GET(GET_GAME_INFO)
    suspend fun getGameInfo(
        @Path("gameId") gameId: Int,
        @Path("drawId") drawId: Int
    ): Response<GameRoundResponse>

    @GET(GAME_RESULTS)
    suspend fun getGameResult(
        @Path("gameId") gameId: Int,
        @Path("fromDate") fromDate: String,
        @Path("toDate") toDate: String
    ): Response<GameResultsResponse>
}