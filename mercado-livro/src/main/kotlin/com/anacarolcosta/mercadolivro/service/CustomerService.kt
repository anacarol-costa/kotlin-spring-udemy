package com.anacarolcosta.mercadolivro.service

import com.anacarolcosta.mercadolivro.model.CustomerModel
import com.anacarolcosta.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service

//onde fica todas as regras de negocio

@Service
class CustomerService(
    val customerRepository: CustomerRepository
) {

    fun getAll(name: String?): List<CustomerModel> {
        name?.let {
            return customerRepository.findByNameContaining(it)
        } //entra na funcao se a variavel name não for nula
        return customerRepository.findAll().toList()
    }

    fun getCustomer(id: Int): CustomerModel {
       return customerRepository.findById(id).orElseThrow()
    }

    fun create(customer: CustomerModel) {
        customerRepository.save(customer)
    }

    fun update(customer: CustomerModel) {
        if (!customerRepository.existsById(customer.id!!)){
            throw Exception()
        } //vai entrar aqui se o id nao existit

            customerRepository.save(customer)
    }

    fun delete(id: Int) {
        if (!customerRepository.existsById(id)){
            throw Exception()
        } //vai entrar aqui se o id nao existit

        customerRepository.deleteById(id)
    }
}