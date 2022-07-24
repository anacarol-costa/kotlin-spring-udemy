package com.anacarolcosta.mercadolivro.controller

import com.anacarolcosta.mercadolivro.controller.request.PostCustomerRequest
import com.anacarolcosta.mercadolivro.controller.request.PutCustomerRequest
import com.anacarolcosta.mercadolivro.controller.response.CustomerResponse
import com.anacarolcosta.mercadolivro.extension.toCustomerModel
import com.anacarolcosta.mercadolivro.extension.toResponse
import com.anacarolcosta.mercadolivro.service.CustomerService
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
class CustomerController (
    val customerService: CustomerService
        ) {

    @GetMapping //recebe dados
    fun getAll(@RequestParam name: String?): List<CustomerResponse> {
       return customerService.getAll(name).map { it.toResponse() }
    } //? indica q eh um atributo n obrigatório, podendo vir com valor nulo

    @GetMapping("/{id}")
    fun getCustomer(@PathVariable id: Int): CustomerResponse {
        return customerService.findById(id).toResponse()
    } //cria path params para retornar na url por id

    @PostMapping //criar recurso/dado
    @ResponseStatus(HttpStatus.CREATED) //notacao q indica a criacao de um objeto
    fun create(@RequestBody customer: PostCustomerRequest) {
        customerService.create(customer.toCustomerModel())
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody customer: PutCustomerRequest) {
        val customerSaved = customerService.findById(id)
        customerService.update(customer.toCustomerModel(customerSaved))
    } //similar ao post. Contudo ele irá apenas atualizar os dados

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        return customerService.delete(id)
    }
}