package com.anacarolcosta.mercadolivro.service

import com.anacarolcosta.mercadolivro.enums.CustomerStatus
import com.anacarolcosta.mercadolivro.enums.Errors
import com.anacarolcosta.mercadolivro.enums.Role
import com.anacarolcosta.mercadolivro.exception.NotFoundException
import com.anacarolcosta.mercadolivro.helper.buildCustomer
import com.anacarolcosta.mercadolivro.model.CustomerModel
import com.anacarolcosta.mercadolivro.repository.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*
import java.util.Random

@ExtendWith(MockKExtension::class)
class CustomerServiceTest {
    @MockK
    private lateinit var customerRepository: CustomerRepository

    @MockK
    private lateinit var bookService: BookService

    @MockK
    private lateinit var bCrypt: BCryptPasswordEncoder

    @InjectMockKs
    @SpyK //ajuda a mockar metodos
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
    fun `deve criar customer e criptografar password`() {
        val initialPassword = Random().nextInt().toString()
        val fakeCustomer = buildCustomer(password = initialPassword)
        val fakePassword = UUID.randomUUID().toString()
        val fakeCustomerEncrypted = fakeCustomer.copy(password = fakePassword)

        every { customerRepository.save(fakeCustomerEncrypted) } returns fakeCustomer
        every { bCrypt.encode(initialPassword) } returns fakePassword

        customerService.create(fakeCustomer)

        verify(exactly = 1) { customerRepository.save(fakeCustomerEncrypted) }
        verify(exactly = 1) { bCrypt.encode(initialPassword) }
    }

    @Test
    fun `deve retornar customer pelo id`() {
        val id = Random().nextInt()
        val fakeCustomer = buildCustomer(id = id)

        every { customerRepository.findById(id) } returns Optional.of(fakeCustomer)

        val customer = customerService.findById(id)

        assertEquals(fakeCustomer, customer)
        verify(exactly = 1) { customerRepository.findById(id) }
    }


    @Test
    fun `deve indicar erro quando o customer não for encontrado pelo id`() {
        val id = Random().nextInt()

        every { customerRepository.findById(id) } returns Optional.empty() //deve retornar vazio

        val error = assertThrows<NotFoundException>{
            customerService.findById(id)
        }

        assertEquals("Customer [${id}] not exists!", error.message)
        assertEquals("ML-201", error.errorCode)

        verify(exactly = 1) { customerRepository.findById(id) }
    }

    @Test
    fun `deve atualizar customer`() {
        val id = Random().nextInt()
        val fakeCustomer = buildCustomer(id = id)

        every { customerRepository.existsById(id) } returns true
        every { customerRepository.save(fakeCustomer) } returns fakeCustomer

        customerService.update(fakeCustomer)

        verify(exactly = 1) { customerRepository.existsById(id) }
        verify(exactly = 1) { customerRepository.save(fakeCustomer) }
    }

    @Test
    fun `deve indicar erro quando o customer não for atualizado`() {
        val id = Random().nextInt()
        val fakeCustomer = buildCustomer(id = id)

        every { customerRepository.existsById(id) } returns false
        every { customerRepository.save(fakeCustomer) } returns fakeCustomer

        val error = assertThrows<NotFoundException>{
            customerService.update(fakeCustomer)
        }

        assertEquals("Customer [${id}] not exists!", error.message)
        assertEquals("ML-201", error.errorCode)

        verify(exactly = 1) { customerRepository.existsById(id) }
        verify(exactly = 0) { customerRepository.save(any()) } // se o customer não existe, o save não deve ser chamado
    }

    @Test
    fun `deve deletar customer`() {
        val id = Random().nextInt()
        val fakeCustomer = buildCustomer(id = id)
        val expectedCustomer = fakeCustomer.copy(status = CustomerStatus.INATIVO)

        every { customerService.findById(id) } returns fakeCustomer
        every { customerRepository.save(expectedCustomer) } returns expectedCustomer
        every { bookService.deleteByCustomer(fakeCustomer) } just runs //não retorna nada

        customerService.delete(id)

        verify(exactly = 1) { customerService.findById(id) }
        verify(exactly = 1) { bookService.deleteByCustomer(fakeCustomer) }
        verify(exactly = 1) { customerRepository.save(expectedCustomer) }
    }

    @Test
    fun `deve indicar erro se não conseguir deletar customer`() {
        val id = Random().nextInt()

        every { customerService.findById(id) } throws NotFoundException(Errors.ML201.message.format(id), Errors.ML201.code)

        val error = assertThrows<NotFoundException>{
            customerService.delete(id)
        }

        assertEquals("Customer [${id}] not exists!", error.message)
        assertEquals("ML-201", error.errorCode)

        verify(exactly = 1) { customerService.findById(id) }
        verify(exactly = 0) { bookService.deleteByCustomer(any()) }
        verify(exactly = 0) { customerRepository.save(any()) }
    }

    @Test
    fun `deve retornar true quando o email for acessível`() {
        val email = "${Random().nextInt()}@email.com"

        every { customerRepository.existsByEmail(email) } returns false

        val emailAvailable = customerService.emailAvailable(email)

        assertTrue(emailAvailable)
        verify(exactly = 1) { customerRepository.existsByEmail(email) }
    }

    @Test
    fun `deve retornar false quando o email for inacessível`() {
        val email = "${Random().nextInt()}@email.com"

        every { customerRepository.existsByEmail(email) } returns true

        val emailAvailable = customerService.emailAvailable(email)

        assertFalse(emailAvailable)
        verify(exactly = 1) { customerRepository.existsByEmail(email) }
    }
}