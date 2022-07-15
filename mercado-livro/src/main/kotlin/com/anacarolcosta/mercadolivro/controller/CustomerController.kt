package com.anacarolcosta.mercadolivro.controller

import com.anacarolcosta.mercadolivro.controller.request.PostCustomerRequest
import com.anacarolcosta.mercadolivro.model.CustomerModel
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("customers") //pasta ou caminho do endpoint
class CustomerController {

    val customers = mutableListOf<CustomerModel>() //cria uma lista mutavel de algum tipo de dados

    @GetMapping //recebe dados
    fun getCustomer(): MutableList<CustomerModel> {
        return customers
    } //onde irá retornar o customerModel

    @PostMapping //criar recurso/dado
    @ResponseStatus(HttpStatus.CREATED) //notacao q indica a criacao de um objeto
    fun create(@RequestBody customer: PostCustomerRequest) {
        val id = if (customers.isEmpty()) {
            1
        } else {
            customers.last().id.toInt() + 1
        }.toString() //funcao para criar de forma dinamica e crescente o id do novo objeto

        customers.add(CustomerModel(id, customer.name, customer.email)) //criando o objeto
    }
}