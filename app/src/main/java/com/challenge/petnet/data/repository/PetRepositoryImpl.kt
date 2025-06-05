package com.challenge.petnet.data.repository

import com.challenge.petnet.data.mapper.toDetailItem
import com.challenge.petnet.data.mapper.toItem
import com.challenge.petnet.data.remote.PetApiService
import com.challenge.petnet.data.utils.handleException
import com.challenge.petnet.domain.model.DetailItem
import com.challenge.petnet.domain.model.Item
import com.challenge.petnet.domain.repository.PetRepository

class PetRepositoryImpl(
    private val api: PetApiService
) : PetRepository {

    override suspend fun getItems(): Result<List<Item>> {
        return try {
            val response = api.getItems()
            if (response.isEmpty()) {
                Result.failure(Exception("Nenhum item encontrado"))
            } else {
                Result.success(response.map { it.toItem() })
            }
        } catch (e: Exception) {
            Result.failure(handleException(e))
        }
    }

    override suspend fun getItemDetail(id: Int): Result<DetailItem> {
        return try {
            val response = api.getItemDetail(id)
            Result.success(response.toDetailItem())
        } catch (e: Exception) {
            Result.failure(handleException(e))
        }
    }
}
