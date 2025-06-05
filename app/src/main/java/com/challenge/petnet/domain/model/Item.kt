package com.challenge.petnet.domain.model

import java.math.BigDecimal

data class Item(
    val id: Int,
    val name: String,
    val price: BigDecimal,
    val imageUrl: String
)