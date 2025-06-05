package com.challenge.petnet

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.challenge.petnet.presentation.cart.viewmodel.CartViewModel
import com.challenge.petnet.presentation.home.ui.HomeContent
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal

class HomeContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeContent_displaysItemsAndCartInfo() {

        val mockCartViewModel = mockk<CartViewModel>(relaxed = true) {
            every { totalItems } returns MutableStateFlow(2)
            every { totalPrice } returns MutableStateFlow(BigDecimal("59.99"))
        }

        composeTestRule.setContent {
            HomeContent(
                onItemClick = {},
                cartViewModel = mockCartViewModel,
                goCartScreen = {}
            )
        }

        composeTestRule
            .onNodeWithText("Buscar")
            .assertIsDisplayed()
    }

}

