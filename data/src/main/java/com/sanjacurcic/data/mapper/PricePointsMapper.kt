package com.sanjacurcic.data.mapper

import com.sanjacurcic.data.base.functional.IMapper
import com.sanjacurcic.data.dto.PricePointsResponse
import com.sanjacurcic.data.model.PricePointsModel

class PricePointsMapper : IMapper<PricePointsResponse, PricePointsModel> {
    override fun map(from: PricePointsResponse?): PricePointsModel {
        return PricePointsModel(
            addOn = from?.addOn?.map { AddOnMapper().map(it) } ?: emptyList(),
            amount = from?.amount ?: 0.0
        )
    }
}