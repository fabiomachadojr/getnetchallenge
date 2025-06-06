package com.challenge.petnet.presentation.cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.petnet.domain.model.CartItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.math.BigDecimal


class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    val totalItems: StateFlow<Int> = _cartItems
        .map { items -> items.sumOf { it.quantity } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    val totalPrice: StateFlow<BigDecimal> = _cartItems
        .map { items ->
            items.sumOf {
                it.item.price * it.quantity.toBigDecimal()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            initialValue = BigDecimal.ZERO
        )

    fun calculateItemTotal(cartItem: CartItem): BigDecimal {
        return cartItem.item.price * cartItem.quantity.toBigDecimal()
    }

    fun addToCart(cartItem: CartItem) {
        val existing = _cartItems.value.find { it.item.id == cartItem.item.id }
        _cartItems.value = if (existing != null) {
            _cartItems.value.map {
                if (it.item.id == cartItem.item.id) it.copy(quantity = it.quantity + 1) else it
            }
        } else {
            _cartItems.value + cartItem
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }


    fun buildSuccessMessage(): String {
        val sb = StringBuilder("Compra realizada com sucesso!\n\nItens:\n")
        _cartItems.value.forEach {
            sb.append(
                "- ${it.item.name} x${it.quantity} = R$ ${
                    "%.2f".format(
                        it.item.price.toDouble() * it.quantity
                    )
                }\n"
            )
        }
        sb.append("\nTotal: R$ ${"%.2f".format(totalPrice.value)}")
        return sb.toString()
    }

}
