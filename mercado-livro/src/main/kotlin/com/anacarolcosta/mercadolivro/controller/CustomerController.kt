package com.anacarolcosta.mercadolivro.controller

import com.anacarolcosta.mercadolivro.model.CustomerModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("customers") //pasta ou caminho do endpoint
class CustomerController {

    @GetMapping //recebe dados
    fun getCustomer(): CustomerModel {
        return CustomerModel("1", "Carol", "carol@email.com")
    } //onde irá retornar o customerModel
}