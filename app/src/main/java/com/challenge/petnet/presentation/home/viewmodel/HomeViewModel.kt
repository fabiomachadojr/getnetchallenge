package com.challenge.petnet.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.petnet.domain.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            _items.value = listOf(
                Item(
                    "1",
                    "Casa DuraPets Dura House Preta para Cães",
                    "R$ 54,39",
                    "https://images.petz.com.br/fotos/10031600000710-2.jpg"
                ),
                Item(
                    "2",
                    "Casa DuraPets Dura House Preta para Cães",
                    "R$ 54,39",
                    "https://images.petz.com.br/fotos/10031600000710-2.jpg"
                ),
                Item(
                    "3",
                    "Casa DuraPets Dura House Preta para Cães",
                    "R$ 54,39",
                    "https://images.petz.com.br/fotos/10031600000710-2.jpg"
                ),
                Item(
                    "4",
                    "Casa DuraPets Dura House Preta para Cães",
                    "R$ 54,39",
                    "https://images.petz.com.br/fotos/10031600000710-2.jpg"
                ),
                Item(
                    "5",
                    "Casa DuraPets Dura House Preta para Cães",
                    "R$ 54,39",
                    "https://images.petz.com.br/fotos/10031600000710-2.jpg"
                ),
                Item(
                    "6",
                    "Casa DuraPets Dura House Preta para Cães",
                    "R$ 54,39",
                    "https://images.petz.com.br/fotos/10031600000710-2.jpg"
                )
            )
        }
    }
}
