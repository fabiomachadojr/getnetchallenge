package com.challenge.petnet.domain.usecase

import com.challenge.petnet.domain.model.DetailItem
import com.challenge.petnet.domain.repository.PetRepository

class GetDetailItemUseCase(
    private val repository: PetRepository
) {
    suspend operator fun invoke(id: String): DetailItem {
        return repository.getItemDetail(id)
    }
}