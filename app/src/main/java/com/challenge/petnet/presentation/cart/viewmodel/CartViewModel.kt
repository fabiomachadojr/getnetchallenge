package com.challenge.petnet.presentation.cart.viewmodel

import androidx.lifecycle.ViewModel
import com.challenge.petnet.domain.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.math.BigDecimal

data class CartItem(
    val item: Item,
    val quantity: Int = 0
)

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

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

    fun removeFromCart(item: Item) {
        _cartItems.value = _cartItems.value
            .mapNotNull {
                when {
                    it.item.id != item.id -> it
                    it.quantity > 1 -> it.copy(quantity = it.quantity - 1)
                    else -> null
                }
            }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun getTotalPrice(): BigDecimal {
        return _cartItems.value.sumOf {
            it.item.price
                .replace("R$", "")
                .replace(",", ".")
                .trim()
                .toBigDecimal() * it.quantity.toBigDecimal()
        }
    }

    fun getTotalItems(): Int {
        return _cartItems.value.sumOf { it.quantity }
    }
}
