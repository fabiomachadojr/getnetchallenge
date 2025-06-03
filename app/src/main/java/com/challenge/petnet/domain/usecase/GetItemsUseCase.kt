package com.challenge.petnet.domain.usecase

import com.challenge.petnet.domain.model.Item
import com.challenge.petnet.domain.repository.PetRepository

class GetItemsUseCase(private val repository: PetRepository) {
    suspend operator fun invoke(): Result<List<Item>> {
        return repository.getItems()
    }
}