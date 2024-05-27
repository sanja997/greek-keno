package com.sanjacurcic.data.mapper

import com.sanjacurcic.data.base.functional.IMapper
import com.sanjacurcic.data.dto.WinningNumbersResponse
import com.sanjacurcic.data.model.WinningNumbersModel

class WinningNumbersMapper : IMapper<WinningNumbersResponse, WinningNumbersModel> {
    override fun map(from: WinningNumbersResponse?): WinningNumbersModel {
        return WinningNumbersModel(
            list = from?.list?.map { it } ?: emptyList()
        )
    }
}