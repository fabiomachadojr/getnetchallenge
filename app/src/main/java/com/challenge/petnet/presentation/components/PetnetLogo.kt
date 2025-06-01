package com.challenge.petnet.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.challenge.petnet.R

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