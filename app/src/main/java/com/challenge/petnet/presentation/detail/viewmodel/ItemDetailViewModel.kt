package com.challenge.petnet.presentation.detail.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.petnet.domain.model.DetailItem
import com.challenge.petnet.domain.usecase.GetDetailItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemDetailViewModel(
    private val getDetailItemUseCase: GetDetailItemUseCase
) : ViewModel() {

    private val _item = MutableStateFlow<DetailItem?>(null)
    val item: StateFlow<DetailItem?> = _item

    fun loadItem(id: String) {
        viewModelScope.launch {
            _item.value = getDetailItemUseCase(id)
        }
    }
}
