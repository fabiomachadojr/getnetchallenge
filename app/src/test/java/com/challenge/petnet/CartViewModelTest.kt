package com.challenge.petnet

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challenge.petnet.domain.model.CartItem
import com.challenge.petnet.domain.model.Item
import com.challenge.petnet.presentation.cart.viewmodel.CartViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
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
    fun `addToCart should add item to cart`() = runTest {
        val item =
            Item(1, "Ração", BigDecimal(10), "https://images.petz.com.br/fotos/1666985549004.jpg")
        val cartItem = CartItem(item, 1)

        viewModel.addToCart(cartItem)
        advanceUntilIdle()

        val result = viewModel.cartItems.first()
        Assert.assertEquals(1, result.size)
        Assert.assertEquals("Ração", result[0].item.name)
    }

    @Test
    fun `addToCart should increment quantity if item already exists`() = runTest {
        val item =
            Item(1, "Ração", BigDecimal(10), "https://images.petz.com.br/fotos/1666985549004.jpg")
        val cartItem = CartItem(item, 1)

        viewModel.addToCart(cartItem)
        viewModel.addToCart(cartItem)
        advanceUntilIdle()

        val result = viewModel.cartItems.first()
        Assert.assertEquals(1, result.size)
        Assert.assertEquals(2, result[0].quantity)
    }

    @Test
    fun `totalItems should reflect total quantity`() = runTest {
        val item1 =
            Item(1, "Ração", BigDecimal(10), "https://images.petz.com.br/fotos/1666985549004.jpg")
        val item2 =
            Item(2, "Coleira", BigDecimal(15), "https://images.petz.com.br/fotos/1666985549004.jpg")
        viewModel.addToCart(CartItem(item1, 2))
        viewModel.addToCart(CartItem(item2, 3))
        advanceUntilIdle()

        val total = viewModel.totalItems.drop(1).first()
        Assert.assertEquals(5, total)
    }

    @Test
    fun `totalPrice should correctly sum values`() = runTest {
        val item1 =
            Item(1, "Ração", BigDecimal(10), "https://images.petz.com.br/fotos/1666985549004.jpg")
        val item2 =
            Item(2, "Coleira", BigDecimal(15), "https://images.petz.com.br/fotos/1666985549004.jpg")
        viewModel.addToCart(CartItem(item1, 2))
        viewModel.addToCart(CartItem(item2, 1))
        advanceUntilIdle()

        val total = viewModel.totalPrice.drop(1).first()
        Assert.assertTrue(total.compareTo(BigDecimal("35.00")) == 0)
    }

    @Test
    fun `clearCart should empty the cart`() = runTest {
        val item =
            Item(1, "Ração", BigDecimal(10), "https://images.petz.com.br/fotos/1666985549004.jpg")
        viewModel.addToCart(CartItem(item, 1))
        advanceUntilIdle()

        viewModel.clearCart()
        advanceUntilIdle()

        val items = viewModel.cartItems.first()
        Assert.assertTrue(items.isEmpty())
    }


}
