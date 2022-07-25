package com.anacarolcosta.mercadolivro.exception

import com.anacarolcosta.mercadolivro.controller.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        val erro = ErrorResponse(
            httpCode = 400,
            message = "Este recurso não existe!",
            internalCode = "0001",
            errors = null
        )

        return ResponseEntity(erro, HttpStatus.BAD_REQUEST)
    }
}