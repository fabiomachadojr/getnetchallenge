package com.challenge.petnet.presentation.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.challenge.petnet.core.ui.state.UiState
import com.challenge.petnet.core.ui.theme.AppColors
import com.challenge.petnet.data.mapper.toItem
import com.challenge.petnet.domain.model.CartItem
import com.challenge.petnet.presentation.cart.viewmodel.CartViewModel
import com.challenge.petnet.presentation.detail.viewmodel.ItemDetailViewModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(
    itemId: String,
    viewModel: ItemDetailViewModel = getViewModel(),
    cartViewModel: CartViewModel,
    onBackClick: () -> Unit
) {

    val itemState by viewModel.itemState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadItem(itemId)
    }

    when (itemState) {
        is UiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is UiState.Error -> {
            val error = (itemState as UiState.Error).message
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = error, color = Color.Red)
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { viewModel.loadItem(itemId) }) {
                        Text("Tentar novamente")
                    }
                }
            }
        }

        is UiState.Success -> {
            val item = (itemState as UiState.Success).data

            Scaffold(
                containerColor = MaterialTheme.colorScheme.onBackground,
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Detalhes do item",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = onBackClick) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Voltar"
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = Color.White,
                            navigationIconContentColor = Color.White
                        )
                    )
                },
                bottomBar = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .background(AppColors.Success)
                            .clickable {
                                cartViewModel.addToCart(
                                    CartItem(item = item.toItem(), quantity = 1)
                                )
                                onBackClick()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Adicionar", color = Color.White
                        )
                    }
                },
                content = { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 16.dp)
                        ) {
                            AsyncImage(
                                model = item.imageUrl,
                                contentDescription = item.description,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background)
                                .padding(horizontal = 16.dp)
                        ) {
                            Spacer(Modifier.height(16.dp))

                            Text(
                                color = Color.Black,
                                text = item.description,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    lineHeight = 24.sp,
                                    letterSpacing = 0.sp
                                )
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                color = Color.Black,
                                text = item.price,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                color = Color.Black,
                                text = "- Com capacidade significativa de armazenamento de água;\n" +
                                        "- Fabricado em plástico resistente, leve e fácil de transportar;\n" +
                                        "- Prático, higiênico e combina com a decoração do ambiente;",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                color = Color.Black,
                                text = "Peso: 900g",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                color = Color.Black,
                                text = "Dimensão: 100x70x200",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            )
        }
    }
}
