package com.challenge.petnet.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.petnet.core.ui.state.UiState
import com.challenge.petnet.domain.model.Item
import com.challenge.petnet.domain.usecase.GetItemsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val getItemsUseCase: GetItemsUseCase) : ViewModel() {

    private val _itemsState = MutableStateFlow<UiState<List<Item>>>(UiState.Loading)
    val itemsState: StateFlow<UiState<List<Item>>> = _itemsState

    private var allItems: List<Item> = emptyList()

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            _itemsState.value = UiState.Loading
            val result = getItemsUseCase()
            _itemsState.value = result.fold(
                onSuccess = {
                    allItems = it
                    UiState.Success(it) },
                onFailure = { UiState.Error(it.message ?: "Erro desconhecido") }
            )
        }
    }

    fun searchItems(query: String) {
        val filtered = if (query.isBlank()) {
            allItems
        } else {
            allItems.filter { it.name.contains(query, ignoreCase = true) }
        }
        _itemsState.value = UiState.Success(filtered)
    }

}
