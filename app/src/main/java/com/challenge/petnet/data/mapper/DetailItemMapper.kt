package com.challenge.petnet.data.mapper

import com.challenge.petnet.data.model.DetailItemResponse
import com.challenge.petnet.domain.model.DetailItem

fun DetailItemResponse.toDetailItem(): DetailItem {
    return DetailItem(
        id = id,
        name = name,
        description = description,
        price = price,
        weight = weight,
        dimensions = dimensions,
        imageUrl = imageUrl
    )
}