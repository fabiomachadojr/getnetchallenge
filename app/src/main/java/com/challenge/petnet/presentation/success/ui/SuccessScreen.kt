package com.challenge.petnet.presentation.success.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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

@Composable
fun SuccessScreen(message: String, onBackToHome: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
            .padding(32.dp),
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
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(32.dp))

        ShareOptions(message = message)

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onBackToHome,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Voltar para a Home")
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
        verticalArrangement = Arrangement.spacedBy(12.dp)
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
                Text("Compartilhar no WhatsApp")
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
                Text("Compartilhar por E-mail")
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
                Text("Compartilhar")
            }
        }
    }
}


fun isWhatsAppInstalled(context: Context): Boolean {
    val packages = listOf("com.whatsapp", "com.whatsapp.w4b")
    packages.forEach { pkg ->
        try {
            context.packageManager.getPackageInfo(pkg, 0)
            Log.d("ShareTest", "Found WhatsApp package: $pkg")
            return true
        } catch (e: PackageManager.NameNotFoundException) {
            Log.d("ShareTest", "Package not found: $pkg")
        }
    }
    return false
}


fun isEmailClientAvailable(context: Context): Boolean {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:".toUri()
    }
    return intent.resolveActivity(context.packageManager) != null
}


