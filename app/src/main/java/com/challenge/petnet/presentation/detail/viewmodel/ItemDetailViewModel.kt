package com.challenge.petnet.presentation.detail.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.petnet.core.ui.state.UiState
import com.challenge.petnet.domain.model.DetailItem
import com.challenge.petnet.domain.usecase.GetDetailItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemDetailViewModel(
    private val getDetailItemUseCase: GetDetailItemUseCase
) : ViewModel() {

    private val _itemState = MutableStateFlow<UiState<DetailItem>>(UiState.Loading)
    val itemState: StateFlow<UiState<DetailItem>> = _itemState

    fun loadItem(id: String) {
        viewModelScope.launch {
            _itemState.value = UiState.Loading
            val result = getDetailItemUseCase(id)
            _itemState.value = result.fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message ?: "Erro desconhecido") }
            )
        }
    }

}
