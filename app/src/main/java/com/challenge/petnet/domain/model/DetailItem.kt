package com.challenge.petnet.domain.model

import java.math.BigDecimal

data class DetailItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val weight: BigDecimal,
    val dimensions: String,
    val imageUrl: String
)
