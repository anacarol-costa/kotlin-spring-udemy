package com.anacarolcosta.mercadolivro.controller

import com.anacarolcosta.mercadolivro.controller.request.PostCustomerRequest
import com.anacarolcosta.mercadolivro.helper.buildCustomer
import com.anacarolcosta.mercadolivro.repository.CustomerRepository
import com.anacarolcosta.mercadolivro.security.UserCustomDetails
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.random.Random

@SpringBootTest
@AutoConfigureMockMvc//permite realizar as chamadas http
@ContextConfiguration
@ActiveProfiles("test")//pega o profile de test
@WithMockUser//do pacote spring security test
class CustomerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc //responsavel por chamar todos os endpoint. possibilita teste de integração

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() = customerRepository.deleteAll() //deletar td antes de subir o db

    @AfterEach
    fun tearDown() = customerRepository.deleteAll() //deleta td dps de ser criado

    @Test
    fun `deve retornar todos os customers quando usar get all`() {
        val customer1 = customerRepository.save(buildCustomer())
        val customer2 = customerRepository.save(buildCustomer())

        val andExpect = mockMvc.perform(get("/customers"))//invoca endpoint
            .andExpect(status().isOk)//valida status
            .andExpect(jsonPath("$.length()").value(2)) //valida o tamanhjo da lista
            .andExpect(jsonPath("$[0].id").value(customer1.id)) //valida path
            .andExpect(jsonPath("$[0].name").value(customer1.name))
            .andExpect(jsonPath("$[0].email").value(customer1.email))
            .andExpect(jsonPath("$[0].status").value(customer1.status.name))
            .andExpect(jsonPath("$[1].id").value(customer2.id))
            .andExpect(jsonPath("$[1].name").value(customer2.name))
            .andExpect(jsonPath("$[1].email").value(customer2.email))
            .andExpect(jsonPath("$[1].status").value(customer2.status.name))
    }

    @Test
    fun `deve filtrar todos os customers pelo nome quando usar get all`() {
        val customer1 = customerRepository.save(buildCustomer(name = "Carol"))
        customerRepository.save(buildCustomer(name = "Ravena"))

        val andExpect = mockMvc.perform(get("/customers?name=Ca"))//invoca endpoint
            .andExpect(status().isOk)//valida status
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].id").value(customer1.id)) //valida path
            .andExpect(jsonPath("$[0].name").value(customer1.name))
            .andExpect(jsonPath("$[0].email").value(customer1.email))
            .andExpect(jsonPath("$[0].status").value(customer1.status.name))
    }

    @Test
    fun `deve criar customer`() {
        val request = PostCustomerRequest("fake name", "${Random.nextInt()}@fakeemail.com", "123456")

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated)

        val customers = customerRepository.findAll().toList()
        assertEquals(1, customers.size)//verifica se o customer criado foi realmente inserido na base
        assertEquals(request.name, customers[0].name)
        assertEquals(request.email, customers[0].email)
    }

    @Test
    fun `deve buscar usuario pelo id quando usuario tiver o mesmo id`() {
        val customer = customerRepository.save(buildCustomer())

       mockMvc.perform(get("/customers/0").with(user(UserCustomDetails(customer))))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(customer.id))
            .andExpect(jsonPath("$.name").value(customer.name))
            .andExpect(jsonPath("$.email").value(customer.email))
            .andExpect(jsonPath("$.status").value(customer.status.name))
    }

    @Test
    fun `deve retonar forbidden quando for usuario com id diferente`() {
        val customer = customerRepository.save(buildCustomer())

        mockMvc.perform(get("/customers/0").with(user(UserCustomDetails(customer))))
            .andExpect(status().isForbidden)
            .andExpect(jsonPath("$.httpCode").value(403))//valida o erro
            .andExpect(jsonPath("$.message").value("Access Denied"))
            .andExpect(jsonPath("$.internalCode").value("ML-000"))
    }
}