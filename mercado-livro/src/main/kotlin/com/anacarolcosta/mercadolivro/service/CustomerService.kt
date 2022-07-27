package com.anacarolcosta.mercadolivro.service

import com.anacarolcosta.mercadolivro.enums.CustomerStatus
import com.anacarolcosta.mercadolivro.enums.Errors
import com.anacarolcosta.mercadolivro.enums.Profile
import com.anacarolcosta.mercadolivro.exception.NotFoundException
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
       return customerRepository.findById(id).orElseThrow{ NotFoundException(Errors.ML201.message.format(id), Errors.ML201.code) }
    }

    fun create(customer: CustomerModel) {
        val customerCopy = customer.copy(
            roles = setOf(Profile.CUSTOMER)
        )
        customerRepository.save(customerCopy)
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

    fun emailAvailable(email: String): Boolean {
        return !customerRepository.existsByEmail(email)
    }
}