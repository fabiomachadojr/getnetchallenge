package com.challenge.petnet.presentation.detail.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.petnet.domain.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemDetailViewModel(
//    private val getItemDetailUseCase: GetItemDetailUseCase
) : ViewModel() {

    private val _item = MutableStateFlow<Item?>(null)
    val item: StateFlow<Item?> = _item

    fun loadItem(itemId: String) {
        viewModelScope.launch {
            _item.value = Item(
                "1",
                "Casa DuraPets Dura House Preta para Cães",
                "R$ 54,39",
                "https://images.petz.com.br/fotos/10031600000710-2.jpg"
            )
        }
    }
}
