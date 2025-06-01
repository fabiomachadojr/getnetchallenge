package com.challenge.petnet.domain.model

data class CartItem(
    val item: Item,
    val quantity: Int = 0
)