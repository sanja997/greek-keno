package com.sanjacurcic.data.base.functional

interface IMapper<F, T> {
    fun map(from: F?): T
}