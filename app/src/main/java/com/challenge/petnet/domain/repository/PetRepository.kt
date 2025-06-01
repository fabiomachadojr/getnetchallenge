package com.challenge.petnet.domain.repository

import com.challenge.petnet.domain.model.DetailItem
import com.challenge.petnet.domain.model.Item

interface PetRepository {
    suspend fun getItems(): List<Item>
    suspend fun getItemDetail(id: String): DetailItem
}