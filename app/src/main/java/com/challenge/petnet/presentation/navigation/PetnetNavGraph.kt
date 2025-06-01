package com.challenge.petnet.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.challenge.petnet.presentation.cart.ui.CartScreen
import com.challenge.petnet.presentation.detail.ui.ItemDetailScreen
import com.challenge.petnet.presentation.home.ui.HomeContent
import com.challenge.petnet.presentation.success.ui.SuccessScreen

@Composable
fun PetnetNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.MAIN
    ) {
        navigation(
            startDestination = Routes.HOME,
            route = Routes.MAIN
        ) {

            composable(Routes.HOME) { backStackEntry ->
                val cartViewModel = rememberCartViewModel(navController, backStackEntry)

                HomeContent(
                    cartViewModel = cartViewModel,
                    onItemClick = { itemId ->
                        navController.navigate("${Routes.DETAIL}/$itemId")
                    },
                    goCartScreen = { navController.navigate(Routes.CART) }
                )
            }

            composable(
                route = "${Routes.DETAIL}/{itemId}",
                arguments = listOf(navArgument("itemId") { type = NavType.StringType })
            ) { backStackEntry ->
                val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
                val cartViewModel = rememberCartViewModel(navController, backStackEntry)

                ItemDetailScreen(
                    itemId = itemId,
                    cartViewModel = cartViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(Routes.CART) { backStackEntry ->
                val cartViewModel = rememberCartViewModel(navController, backStackEntry)

                CartScreen(
                    viewModel = cartViewModel,
                    onFinish = {
                        val message = cartViewModel.buildSuccessMessage()
                        val encodedMessage = Uri.encode(message)
                        navController.navigate("${Routes.SUCCESS}?message=$encodedMessage")
                    }
                )
            }

            composable(
                route = "${Routes.SUCCESS}?message={message}",
                arguments = listOf(
                    navArgument("message") {
                        type = NavType.StringType
                        defaultValue = ""
                        nullable = true
                    }
                )
            ) { backStackEntry ->
                val message = backStackEntry.arguments?.getString("message") ?: ""
                val cartViewModel = rememberCartViewModel(navController, backStackEntry)
                SuccessScreen(
                    message = message,
                    onBackToHome = {
                        cartViewModel.clearCart()
                        navController.popBackStack(Routes.HOME, inclusive = false)
                    }
                )
            }
        }
    }
}
