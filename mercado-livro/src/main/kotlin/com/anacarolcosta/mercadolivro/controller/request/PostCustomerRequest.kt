package com.anacarolcosta.mercadolivro.controller.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PostCustomerRequest (

    @field:NotEmpty //não pode ser nulo
    var name: String,

    @field:Email //apenas email valido
    var email: String
)