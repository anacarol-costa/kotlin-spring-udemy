package com.anacarolcosta.mercadolivro.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal


//finalidade: estudo (teste unitario e de integração)
class BookModelTest {

    @Test
    @DisplayName("""
        Dado que um nome menor que 3 caracteres eh validado
        Quando a funcao for chamada
        Entao devera ser retornado false
    """)
    fun isValidName() {
        // setup
        val invalidName = "IA"
        val bookModel = BookModel(1, invalidName, BigDecimal.ZERO, null)
        // execute
        val retorno = bookModel.isValidName()
        // verify
        Assertions.assertTrue(retorno)
    }

    // Teste unitario
    // Teste integracao
    // Teste E2E/ fim a fim
}