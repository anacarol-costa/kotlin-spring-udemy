package com.anacarolcosta.mercadolivro.repository

import com.anacarolcosta.mercadolivro.model.CustomerModel
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CustomerRepository: CrudRepository<CustomerModel, Int> {

    fun findByNameContaining(name: String): List<CustomerModel>
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): CustomerModel?
}