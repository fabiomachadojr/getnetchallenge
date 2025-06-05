package com.challenge.petnet.data.model

import java.math.BigDecimal

data class ItemResponse(
    val id: Int,
    val name: String,
    val price: BigDecimal,
    val imageUrl: String
)