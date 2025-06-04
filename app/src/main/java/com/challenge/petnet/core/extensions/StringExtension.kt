package com.challenge.petnet.core.extensions

import java.math.BigDecimal

fun String.toBigDecimalSafe(): BigDecimal {
    return try {
        this.replace("R$", "").replace(",", ".").trim().toBigDecimal()
    } catch (e: Exception) {
        BigDecimal.ZERO
    }
}
