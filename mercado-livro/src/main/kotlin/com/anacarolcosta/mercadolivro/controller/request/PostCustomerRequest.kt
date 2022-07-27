package com.anacarolcosta.mercadolivro.controller.request

import com.anacarolcosta.mercadolivro.validation.EmailAvailable
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PostCustomerRequest (

    @field:NotEmpty(message = "Nome deve ser informado") //não pode ser nulo
    var name: String,

    @field:Email(message = "E-mail deve ser válido") //apenas email valido
    @EmailAvailable
    var email: String,

    @field:NotEmpty(message = "Senha deve ser informada!")
    var password: String
)