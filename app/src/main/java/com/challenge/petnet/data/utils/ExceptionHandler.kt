package com.challenge.petnet.data.utils

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

fun handleException(e: Exception): Throwable {
    return when (e) {
        is HttpException -> {
            when (e.code()) {
                400 -> Exception("Requisição inválida")
                404 -> Exception("Recurso não encontrado")
                500 -> Exception("Erro interno no servidor")
                else -> Exception("Erro HTTP ${e.code()}")
            }
        }

        is SocketTimeoutException -> Exception("Tempo de resposta excedido")
        is IOException -> Exception("Erro de conexão")
        else -> Exception("Erro desconhecido: ${e.message}")
    }
}
