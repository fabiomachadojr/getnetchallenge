package com.challenge.petnet.core.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.net.toUri

fun getInstalledWhatsAppPackage(context: Context): String? {
    val packages = listOf("com.whatsapp", "com.whatsapp.w4b")
    return packages.firstOrNull { pkg ->
        try {
            context.packageManager.getPackageInfo(pkg, PackageManager.GET_ACTIVITIES)
            true
        } catch (_: PackageManager.NameNotFoundException) {
            false
        }
    }
}

fun isEmailClientAvailable(context: Context): Boolean {
    val intent = Intent(Intent.ACTION_SENDTO, "mailto:".toUri())
    return intent.resolveActivity(context.packageManager) != null
}
