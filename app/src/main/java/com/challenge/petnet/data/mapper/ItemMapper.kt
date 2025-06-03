package com.challenge.petnet.data.mapper

import com.challenge.petnet.data.model.ItemResponse
import com.challenge.petnet.domain.model.DetailItem
import com.challenge.petnet.domain.model.Item

fun ItemResponse.toItem(): Item {
    return Item(
        id = id,
        description = description,
        price = price,
        imageUrl = imageUrl
    )
}

fun DetailItem.toItem(): Item {
    return Item(
        id = id,
        description = description,
        price = price,
        imageUrl = imageUrl
    )
}