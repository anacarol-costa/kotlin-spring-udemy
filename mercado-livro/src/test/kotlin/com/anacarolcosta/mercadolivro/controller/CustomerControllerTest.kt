package com.anacarolcosta.mercadolivro.controller

import com.anacarolcosta.mercadolivro.helper.buildCustomer
import com.anacarolcosta.mercadolivro.repository.CustomerRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

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
    fun `deve retornar todos os customers`() {
        val customer1 = customerRepository.save(buildCustomer())
        val customer2 = customerRepository.save(buildCustomer())

        val andExpect = mockMvc.perform(get("/customers"))//invoca endpoint
            .andExpect(status().isOk)//valida status
            .andExpect(jsonPath("$[0].id").value(customer1.id)) //valida path
            .andExpect(jsonPath("$[0].name").value(customer1.name))
            .andExpect(jsonPath("$[0].email").value(customer1.email))
            .andExpect(jsonPath("$[0].status").value(customer1.status.name))
            .andExpect(jsonPath("$[1].id").value(customer2.id))
            .andExpect(jsonPath("$[1].name").value(customer2.name))
            .andExpect(jsonPath("$[1].email").value(customer2.email))
            .andExpect(jsonPath("$[1].status").value(customer2.status.name))
    }
}