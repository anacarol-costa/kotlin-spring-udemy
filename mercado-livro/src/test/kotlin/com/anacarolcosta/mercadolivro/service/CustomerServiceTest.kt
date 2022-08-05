package com.anacarolcosta.mercadolivro.service

import com.anacarolcosta.mercadolivro.enums.CustomerStatus
import com.anacarolcosta.mercadolivro.enums.Role
import com.anacarolcosta.mercadolivro.model.CustomerModel
import com.anacarolcosta.mercadolivro.repository.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@ExtendWith(MockKExtension::class)
class CustomerServiceTest {
    @MockK
    private lateinit var customerRepository: CustomerRepository

    @MockK
    private lateinit var bookService: BookService

    @MockK
    private lateinit var bCrypt: BCryptPasswordEncoder

    @InjectMockKs
    private lateinit var customerService: CustomerService

    @Test
    fun `deve retornar todos os customers` () {
        val fakeCustomer = listOf(buildCustomer(), buildCustomer())

        every { customerRepository.findAll() } returns fakeCustomer

        val customers = customerService.getAll(null)

        assertEquals(fakeCustomer, customers) //verifica se fakecustomers é igual a customers
        verify(exactly = 1) { customerRepository.findAll() } //verifica se o metodo foi realmente chamado
        verify(exactly = 0) { customerRepository.findByNameContaining(any()) } //se o metodo foi chamado mais de uma vez, quer dizer q tem algo errado
    }

    @Test
    fun `deve retornar os customers quando o nome for informado` () {
        val name = Math.random().toString() //usar sempre strings aleatorias ou UUID.randomUUID().toString()
        val fakeCustomer = listOf(buildCustomer(), buildCustomer())

        every { customerRepository.findByNameContaining(name) } returns fakeCustomer

        val customers = customerService.getAll(name)

        assertEquals(fakeCustomer, customers)
        verify(exactly = 0) { customerRepository.findAll() } //não deve ser chamado
        verify(exactly = 1) { customerRepository.findByNameContaining(name) } //metodo a ser chamado
    }

    @Test
    fun `deve criar customer e criptografar password` () {
        val initialPassword = Math.random().toString()
        val fakeCustomer = buildCustomer(password = initialPassword)
        val fakePassword = UUID.randomUUID().toString()
        val fakeCustomerEncrypted = fakeCustomer.copy(password = fakePassword)

        every { customerRepository.save(fakeCustomerEncrypted) } returns fakeCustomer
        every { bCrypt.encode(initialPassword) } returns fakePassword

        customerService.create(fakeCustomer)

        verify(exactly = 1) { customerRepository.save(fakeCustomerEncrypted) }
        verify(exactly = 1) { bCrypt.encode(initialPassword) }
    }

    fun buildCustomer(
        id: Int? = null,
        name: String = "nome do customer",
        email: String = "${UUID.randomUUID()}@email.com",
        password: String = "senha"
    ) = CustomerModel(
        id = id,
        name = name,
        email = email,
        status = CustomerStatus.ATIVO,
        password = password,
        roles = setOf(Role.CUSTOMER)
    )
}