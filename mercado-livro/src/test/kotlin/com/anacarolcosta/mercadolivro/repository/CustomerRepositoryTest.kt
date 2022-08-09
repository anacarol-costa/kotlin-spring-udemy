package com.anacarolcosta.mercadolivro.repository

import com.anacarolcosta.mercadolivro.helper.buildCustomer
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerRepositoryTest {

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun setup() = customerRepository.deleteAll() //vai deletar td antes de iniciar o teste

    @Test
    fun `deve retornar nome contendo`() {
        val carol = customerRepository.save(buildCustomer(name = "Carol"))//insere registros
        val carola = customerRepository.save(buildCustomer(name = "Carola"))//insere registros
        customerRepository.save(buildCustomer(name = "Ana"))

        val customers = customerRepository.findByNameContaining("Ca")

        assertEquals(listOf(carol, carola), customers)
    }

    @Nested
    inner class `exists by email` {
        @Test
        fun `deve retornar true quando o email existir`() {
            val email = "email@teste.com"

            customerRepository.save(buildCustomer(email = email))

            val exists = customerRepository.existsByEmail(email)

            assertTrue(exists)
        }

        @Test
        fun `deve retornar false quando não existir o email`() {
            val email = "emailnaoexiste@teste.com"

            val exists = customerRepository.existsByEmail(email)

            assertFalse(exists)
        }
    }

    @Nested
    inner class `find by email` {
        @Test
        fun `deve retornar customer quando o email existir`() {
            val email = "email@teste.com"

            val customer = customerRepository.save(buildCustomer(email = email))

            val result = customerRepository.findByEmail(email)

            assertNotNull(result) //indica que o result não é nulo
            assertEquals(customer, result)
        }

        @Test
        fun `deve retornar null quando não existir o email`() {
            val email = "emailnaoexiste@teste.com"

            val result = customerRepository.findByEmail(email)

            assertNull(result)
        }
    }
}