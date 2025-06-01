package com.challenge.petnet.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.challenge.petnet.presentation.cart.viewmodel.CartViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun rememberCartViewModel(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry
): CartViewModel {
    val parentEntry = remember(backStackEntry) {
        navController.getBackStackEntry(Routes.MAIN)
    }
    return getViewModel(viewModelStoreOwner = parentEntry)
}
