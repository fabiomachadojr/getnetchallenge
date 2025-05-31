package com.challenge.petnet.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.challenge.petnet.R


@Composable
fun CartButton(
    itemCount: Int,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(start = 50.dp),
                text = "Ir p/ carrinho",
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )

            if (itemCount > 0) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 8.dp, y = (-8).dp)
                        .size(24.dp)
                        .background(Color.Black, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = itemCount.toString(),
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Image(
            painter = painterResource(id =R.drawable.cart_icon),
            modifier = Modifier
                .padding(end = 130.dp, bottom = 6.dp)
                .size(60.dp)
                .align(Alignment.Center),
            contentDescription = "Carrinho",
        )

    }
}
