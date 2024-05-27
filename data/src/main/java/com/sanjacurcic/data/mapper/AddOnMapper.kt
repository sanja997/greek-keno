package com.sanjacurcic.data.mapper

import com.sanjacurcic.data.base.functional.IMapper
import com.sanjacurcic.data.dto.AddOnResponse
import com.sanjacurcic.data.model.AddOnModel

class AddOnMapper : IMapper<AddOnResponse, AddOnModel> {
    override fun map(from: AddOnResponse?): AddOnModel {
        return AddOnModel(
            amount = from?.amount ?: 0.0,
            gameType = from?.gameType.orEmpty()
        )
    }
}