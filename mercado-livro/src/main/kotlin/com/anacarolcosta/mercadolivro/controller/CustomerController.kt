package com.anacarolcosta.mercadolivro.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController {

    @GetMapping //recebe dados
    fun helloWorld(): String {
        return "Hello World"
    }
}