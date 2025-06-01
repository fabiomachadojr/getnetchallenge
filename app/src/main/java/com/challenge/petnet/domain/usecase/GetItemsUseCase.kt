package com.challenge.petnet.domain.usecase

import com.challenge.petnet.domain.repository.PetRepository

class GetItemsUseCase(private val repository: PetRepository) {
    suspend operator fun invoke() = repository.getItems()
}