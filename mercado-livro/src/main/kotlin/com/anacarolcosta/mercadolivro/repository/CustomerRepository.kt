package com.anacarolcosta.mercadolivro.repository

import com.anacarolcosta.mercadolivro.model.CustomerModel
import org.springframework.data.repository.CrudRepository

interface CustomerRepository: CrudRepository<CustomerModel, Int> {

    fun findByNameContaining(name: String): List<CustomerModel>
}