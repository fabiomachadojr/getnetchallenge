package com.challenge.petnet.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.petnet.domain.model.Item
import com.challenge.petnet.domain.usecase.GetItemsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val getItemsUseCase: GetItemsUseCase) : ViewModel() {

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            try {
                _items.value = getItemsUseCase()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
