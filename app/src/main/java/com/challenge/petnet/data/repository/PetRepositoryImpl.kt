package com.challenge.petnet.data.repository

import com.challenge.petnet.data.mapper.toDetailItem
import com.challenge.petnet.data.mapper.toItem
import com.challenge.petnet.data.remote.PetApiService
import com.challenge.petnet.domain.model.DetailItem
import com.challenge.petnet.domain.model.Item
import com.challenge.petnet.domain.repository.PetRepository


class PetRepositoryImpl(
    private val api: PetApiService
) : PetRepository {

    override suspend fun getItems(): List<Item> {
        return api.getItems().map { it.toItem() }
    }

    override suspend fun getItemDetail(id: String): DetailItem {
        return api.getItemDetail(id).toDetailItem()
    }
}