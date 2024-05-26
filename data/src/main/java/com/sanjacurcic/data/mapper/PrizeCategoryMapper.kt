package com.sanjacurcic.data.mapper

import com.sanjacurcic.data.base.functional.IMapper
import com.sanjacurcic.data.dto.PriceCategoryResponse
import com.sanjacurcic.data.model.PriceCategoryModel

class PrizeCategoryMapper : IMapper<PriceCategoryResponse, PriceCategoryModel> {
    override fun map(from: PriceCategoryResponse?): PriceCategoryModel {
        return PriceCategoryModel(
            id = from?.id ?: 0,
            fixed = from?.fixed ?: 0.0,
            gameType = from?.gameType.orEmpty()
        )
    }
}