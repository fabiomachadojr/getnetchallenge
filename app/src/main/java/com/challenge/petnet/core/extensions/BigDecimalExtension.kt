package com.challenge.petnet.core.extensions

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

fun BigDecimal.toBrazilCurrency(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return formatter.format(this)
}

fun BigDecimal.formatWeight(): String {
    return if (this < BigDecimal("1.0")) {
        val grams = this.multiply(BigDecimal(1000))
        "${grams.stripTrailingZeros().toPlainString()} g"
    } else {
        "${this.stripTrailingZeros().toPlainString()} kg"
    }
}