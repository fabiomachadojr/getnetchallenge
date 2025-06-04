package com.challenge.petnet.presentation.cart.viewmodel

import androidx.lifecycle.ViewModel
import com.challenge.petnet.domain.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.math.BigDecimal

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    private val _totalItems = MutableStateFlow(0)
    val totalItems: StateFlow<Int> = _totalItems

    private val _totalPrice = MutableStateFlow(BigDecimal.ZERO)
    val totalPrice: StateFlow<BigDecimal> = _totalPrice


    fun addToCart(cartItem: CartItem) {
        val existing = _cartItems.value.find { it.item.id == cartItem.item.id }
        _cartItems.value = if (existing != null) {
            _cartItems.value.map {
                if (it.item.id == cartItem.item.id) it.copy(quantity = it.quantity + 1) else it
            }
        } else {
            _cartItems.value + cartItem
        }
        updateTotals()
    }

    fun clearCart() {
        _cartItems.value = emptyList()
        updateTotals()
    }

    private fun updateTotals() {
        _totalItems.value = _cartItems.value.sumOf { it.quantity }
        _totalPrice.value = _cartItems.value.sumOf {
            it.item.price
                .replace("R$", "")
                .replace(",", ".")
                .trim()
                .toBigDecimal() * it.quantity.toBigDecimal()
        }
    }

    fun buildSuccessMessage(): String {
        if (_cartItems.value.isEmpty()) return "Seu carrinho está vazio."

        val sb = StringBuilder("Compra realizada com sucesso!\n\nItens:\n")
        _cartItems.value.forEach {
            sb.append(
                "- ${it.item.description} x${it.quantity} = R$ ${
                    "%.2f".format(
                        it.item.price.replace("R$", "").replace(",", ".").toDouble() * it.quantity
                    )
                }\n"
            )
        }
        sb.append("\nTotal: R$ ${"%.2f".format(_totalPrice.value)}")
        return sb.toString()
    }

}
