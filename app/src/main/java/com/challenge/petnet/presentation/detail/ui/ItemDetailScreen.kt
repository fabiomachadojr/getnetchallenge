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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.challenge.petnet.domain.model.Item
import com.challenge.petnet.presentation.cart.viewmodel.CartItem
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
    val item by viewModel.item.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadItem(itemId)
    }

    if (item == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            containerColor = Color.White,
            topBar = {
                TopAppBar(
                    title = { Text("Detalhes do Item") },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Red,
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
                        .background(Color.Green)
                        .clickable {
                            cartViewModel.addToCart(
                                CartItem(
                                    item = Item(
                                        "1",
                                        "Casa DuraPets Dura House Preta para Cães",
                                        "R$ 54,39",
                                        "https://images.petz.com.br/fotos/10031600000710-2.jpg"
                                    ), quantity = 1
                                )
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Adicionar", color = Color.White)
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
                            .background(Color.White)
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        AsyncImage(
                            model = item!!.imageUrl,
                            contentDescription = item!!.description,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray)
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        Text(
                            color = Color.Black,
                            text = item!!.description,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            color = Color.Black,
                            text = item!!.price,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            color = Color.Black,
                            text = "Descrição do item...",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

            }
        )
    }
}
