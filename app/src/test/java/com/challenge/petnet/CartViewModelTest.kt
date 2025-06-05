package com.challenge.petnet

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challenge.petnet.domain.model.CartItem
import com.challenge.petnet.domain.model.Item
import com.challenge.petnet.presentation.cart.viewmodel.CartViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.math.BigDecimal

@ExperimentalCoroutinesApi
class CartViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: CartViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CartViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addToCart deve adicionar item ao carrinho`() = runTest {
        val item = Item("1", "Ração", "R$ 10,00", "")
        val cartItem = CartItem(item, 1)

        viewModel.addToCart(cartItem)
        advanceUntilIdle()

        val result = viewModel.cartItems.first()
        Assert.assertEquals(1, result.size)
        Assert.assertEquals("Ração", result[0].item.name)
    }

    @Test
    fun `addToCart deve incrementar quantidade se item já existir`() = runTest {
        val item = Item("1", "Ração", "R$ 10,00", "")
        val cartItem = CartItem(item, 1)

        viewModel.addToCart(cartItem)
        viewModel.addToCart(cartItem)
        advanceUntilIdle()

        val result = viewModel.cartItems.first()
        Assert.assertEquals(1, result.size)
        Assert.assertEquals(2, result[0].quantity)
    }

    @Test
    fun `totalItems deve refletir quantidade total`() = runTest {
        val item1 = Item("1", "Ração", "R$ 10,00", "")
        val item2 = Item("2", "Coleira", "R$ 15,00", "")
        viewModel.addToCart(CartItem(item1, 2))
        viewModel.addToCart(CartItem(item2, 3))
        advanceUntilIdle()

        val total = viewModel.totalItems.first()
        Assert.assertEquals(5, total)
    }

    @Test
    fun `totalPrice deve somar corretamente os valores`() = runTest {
        val item1 = Item("1", "Ração", "R$ 10,00", "")
        val item2 = Item("2", "Coleira", "R$ 15,00", "")
        viewModel.addToCart(CartItem(item1, 2)) // 20.00
        viewModel.addToCart(CartItem(item2, 1)) // 15.00
        advanceUntilIdle()

        val total = viewModel.totalPrice.first()
        Assert.assertEquals(BigDecimal("35.00"), total)
    }

    @Test
    fun `clearCart deve esvaziar o carrinho`() = runTest {
        val item = Item("1", "Ração", "R$ 10,00", "")
        viewModel.addToCart(CartItem(item, 1))
        advanceUntilIdle()

        viewModel.clearCart()
        advanceUntilIdle()

        val items = viewModel.cartItems.first()
        Assert.assertTrue(items.isEmpty())
    }

    @Test
    fun `buildSuccessMessage deve gerar mensagem formatada corretamente`() = runTest {
        val item1 = Item("1", "Ração", "R$ 10,00", "")
        val item2 = Item("2", "Coleira", "R$ 15,00", "")
        viewModel.addToCart(CartItem(item1, 1))
        viewModel.addToCart(CartItem(item2, 2))
        advanceUntilIdle()

        val message = viewModel.buildSuccessMessage()

        Assert.assertTrue(message.contains("Ração x1 = R$ 10.00"))
        Assert.assertTrue(message.contains("Coleira x2 = R$ 30.00"))
        Assert.assertTrue(message.contains("Total: R$ 40.00"))
    }

    @Test
    fun `buildSuccessMessage deve retornar mensagem de carrinho vazio`() = runTest {
        val message = viewModel.buildSuccessMessage()
        Assert.assertEquals("Seu carrinho está vazio.", message)
    }
}
