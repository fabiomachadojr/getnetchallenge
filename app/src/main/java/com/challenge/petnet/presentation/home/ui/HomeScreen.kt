package com.challenge.petnet.presentation.home.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.challenge.petnet.R
import com.challenge.petnet.core.ui.theme.PetnetTheme
import com.challenge.petnet.presentation.cart.viewmodel.CartViewModel
import com.challenge.petnet.presentation.components.CartButton
import com.challenge.petnet.presentation.components.ItemCard
import com.challenge.petnet.presentation.detail.ui.ItemDetailScreen
import com.challenge.petnet.presentation.home.viewmodel.HomeViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetnetTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "main"
                ) {
                    navigation(startDestination = "home", route = "main") {
                        composable("home") { backStackEntry ->
                            val parentEntry =
                                remember(backStackEntry) { navController.getBackStackEntry("main") }
                            val cartViewModel: CartViewModel =
                                getViewModel(viewModelStoreOwner = parentEntry)

                            HomeContent(
                                cartViewModel = cartViewModel,
                                onItemClick = { itemId ->
                                    navController.navigate("detail/$itemId")
                                }
                            )
                        }

                        composable(
                            route = "detail/{itemId}",
                            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
                            val parentEntry =
                                remember(backStackEntry) { navController.getBackStackEntry("main") }
                            val cartViewModel: CartViewModel =
                                getViewModel(viewModelStoreOwner = parentEntry)

                            ItemDetailScreen(
                                itemId = itemId,
                                cartViewModel = cartViewModel,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }

            }
        }
    }

    @Composable
    fun PetnetLogo() {
        Image(
            painter = painterResource(id = R.drawable.logo_petnet),
            contentDescription = "Logo Petnet",
            modifier = Modifier
                .height(60.dp)
                .size(250.dp)
                .padding(top = 16.dp),
            alignment = Alignment.Center

        )
    }

    @Composable
    fun HomeContent(onItemClick: (Any?) -> Unit, cartViewModel: CartViewModel) {
        val viewModel: HomeViewModel = getViewModel()
        val items by viewModel.items.collectAsState()

        val cartItems by cartViewModel.cartItems.collectAsState()
        val totalItems = cartItems.sumOf { it.quantity }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F2F3))
        ) {

            Spacer(modifier = Modifier.height(22.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                PetnetLogo()
            }

            Spacer(modifier = Modifier.height(22.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Buscar") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(start = 16.dp, end = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                itemsIndexed(items) { index, item ->
                    val modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (index == 0 || index == 1) Modifier.padding(top = 16.dp) else Modifier
                        )

                    ItemCard(
                        item = item,
                        modifier = modifier,
                        onClick = { onItemClick("1") }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color.Red),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.4f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "TOTAL:\nR$ ${cartViewModel.getTotalPrice()}",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(0.6f)
                        .fillMaxWidth()
                ) {
                    CartButton(itemCount = totalItems, onClick = {})
                }
            }
        }
    }
}

