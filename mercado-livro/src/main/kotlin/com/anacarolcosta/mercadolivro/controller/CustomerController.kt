package com.anacarolcosta.mercadolivro.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("customer") //pasta ou caminho do endpoint
class CustomerController {

    @GetMapping //recebe dados
    fun helloWorld(): String {
        return "Customer 1"
    }
}