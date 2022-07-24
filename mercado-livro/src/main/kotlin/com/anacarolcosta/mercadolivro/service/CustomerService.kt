package com.anacarolcosta.mercadolivro.service

import com.anacarolcosta.mercadolivro.enums.CustomerStatus
import com.anacarolcosta.mercadolivro.model.CustomerModel
import com.anacarolcosta.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service

//onde fica todas as regras de negocio

@Service
class CustomerService(
    val customerRepository: CustomerRepository,
    val bookService: BookService
) {

    fun getAll(name: String?): List<CustomerModel> {
        name?.let {
            return customerRepository.findByNameContaining(it)
        } //entra na funcao se a variavel name não for nula
        return customerRepository.findAll().toList()
    }

    fun findById(id: Int): CustomerModel {
       return customerRepository.findById(id).orElseThrow()
    }

    fun create(customer: CustomerModel) {
        customerRepository.save(customer)
    }

    fun update(customer: CustomerModel) {
        if (!customerRepository.existsById(customer.id!!)){
            throw Exception()
        } //vai entrar aqui se o id nao existir

            customerRepository.save(customer)
    }

    fun delete(id: Int) {
        val customer = findById(id)

        bookService.deleteByCustomer(customer)

        customer.status = CustomerStatus.INATIVO

        customerRepository.save(customer)
    }
}