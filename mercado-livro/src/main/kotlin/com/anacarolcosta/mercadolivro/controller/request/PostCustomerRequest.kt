package com.anacarolcosta.mercadolivro.controller.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PostCustomerRequest (

    @field:NotEmpty(message = "Nome deve ser informado") //não pode ser nulo
    var name: String,

    @field:Email(message = "E-mail deve ser válido") //apenas email valido
    var email: String
)