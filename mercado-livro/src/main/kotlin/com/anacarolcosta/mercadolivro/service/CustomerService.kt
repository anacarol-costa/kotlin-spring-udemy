package com.anacarolcosta.mercadolivro.service

import com.anacarolcosta.mercadolivro.controller.request.PostCustomerRequest
import com.anacarolcosta.mercadolivro.controller.request.PutCustomerRequest
import com.anacarolcosta.mercadolivro.model.CustomerModel
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

//onde fica todas as regras de negocio

@Service
class CustomerService {
    val customers = mutableListOf<CustomerModel>() //cria uma lista mutavel de algum tipo de dados

    fun getAll(name: String?): List<CustomerModel> {
        name?.let {
            return customers.filter { it.name.contains(name, ignoreCase = true) }
        } //entra na funcao se a variavel name não for nula
        return customers
    }

    fun getCustomer(id: String): CustomerModel {
        return customers.filter { it.id == id }.first() //retorna o indice, que foi primeiramente encontrado. o it = this
    }

    fun create(customer: CustomerModel) {
        val id = if (customers.isEmpty()) {
            1
        } else {
            customers.last().id!!.toInt() + 1
        }.toString() //funcao para criar de forma dinamica e crescente o id do novo objeto

        customer.id = id

        customers.add(customer)
    }

    fun update(customer: CustomerModel) {
        customers.filter { it.id == customer.id }.first().let {
            it.name = customer.name
            it.email = customer.email
        } //funcao para atualizar o customer
    }

    fun delete(id: String) {
        customers.removeIf { it.id == id }
    }
}