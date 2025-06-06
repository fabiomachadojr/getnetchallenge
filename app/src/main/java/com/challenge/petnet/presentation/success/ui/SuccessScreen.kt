package com.challenge.petnet.presentation.success.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.challenge.petnet.core.utils.getInstalledWhatsAppPackage
import com.challenge.petnet.core.utils.isEmailClientAvailable

@Composable
fun SuccessScreen(message: String, onBackToHome: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
            .padding(32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 72.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Sucesso",
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(96.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Pedido realizado com sucesso!",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(32.dp))

            ShareOptions(message = message)
        }

        OutlinedButton(
            onClick = onBackToHome,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Text("Fechar")
        }
    }
}


@Composable
fun ShareOptions(message: String) {
    val context = LocalContext.current

    val whatsappPackage = remember { getInstalledWhatsAppPackage(context) }
    val isEmailAvailable = remember { isEmailClientAvailable(context) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        whatsappPackage?.let { pkg ->
            Button(onClick = {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    `package` = pkg
                    putExtra(Intent.EXTRA_TEXT, message)
                }
                context.startActivity(intent)
            }) {
                Text("Compartilhar no WhatsApp", color = Color.White)
            }
        }

        if (isEmailAvailable) {
            Button(onClick = {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = "mailto:".toUri()
                    putExtra(Intent.EXTRA_SUBJECT, "Pedido Petnet")
                    putExtra(Intent.EXTRA_TEXT, message)
                }
                context.startActivity(intent)
            }) {
                Text("Compartilhar por E-mail", color = Color.White)
            }
        }

        if (whatsappPackage == null && !isEmailAvailable) {
            Button(onClick = {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, message)
                }
                context.startActivity(Intent.createChooser(intent, "Compartilhar via"))
            }) {
                Text("Compartilhar", color = Color.White)
            }
        }
    }
}

