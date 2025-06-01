package com.challenge.petnet.presentation.home.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.challenge.petnet.core.ui.theme.PetnetTheme
import com.challenge.petnet.presentation.navigation.PetnetNavGraph

class HomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetnetTheme {
                val navController = rememberNavController()
                PetnetNavGraph(navController = navController)
            }
        }
    }
}
