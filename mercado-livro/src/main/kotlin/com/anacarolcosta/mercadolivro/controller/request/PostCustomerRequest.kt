package com.anacarolcosta.mercadolivro.controller.request

import com.anacarolcosta.mercadolivro.model.CustomerModel

data class PostCustomerRequest (
    var name: String,
    var email: String
)