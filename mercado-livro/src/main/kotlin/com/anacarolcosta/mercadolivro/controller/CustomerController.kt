package com.anacarolcosta.mercadolivro.controller

import com.anacarolcosta.mercadolivro.controller.request.PostCustomerRequest
import com.anacarolcosta.mercadolivro.controller.request.PutCustomerRequest
import com.anacarolcosta.mercadolivro.model.CustomerModel
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("customers") //pasta ou caminho do endpoint
class CustomerController {

    val customers = mutableListOf<CustomerModel>() //cria uma lista mutavel de algum tipo de dados

    @GetMapping //recebe dados
    fun getAll(@RequestParam name: String?): List<CustomerModel> {
        name?.let {
            return customers.filter { it.name.contains(name, ignoreCase = true) }
        } //entra na funcao se a variavel name não for nula
        return customers
    } //? indica q eh um atributo n obrigatório, podendo vir com valor nulo

    @GetMapping("/{id}")
    fun getCustomer(@PathVariable id: String): CustomerModel {
        return customers.filter { it.id == id }.first() //retorna o indice, que foi primeiramente encontrado. o it = this
    } //cria path params para retornar na url por id

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

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: String, @RequestBody customer: PutCustomerRequest) {
        customers.filter { it.id ==id }.first().let {
            it.name = customer.name
            it.email = customer.email
        } //funcao para atualizar o customer
    } //similar ao post. Contudo ele irá apenas atualizar os dados

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) {
        customers.removeIf { it.id == id }
    }
}