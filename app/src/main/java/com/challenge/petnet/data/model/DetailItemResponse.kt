package com.challenge.petnet.data.model

import java.math.BigDecimal

data class DetailItemResponse(
    val id: Int,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val weight: BigDecimal,
    val dimensions: String,
    val imageUrl: String
)