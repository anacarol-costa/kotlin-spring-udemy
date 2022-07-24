package com.anacarolcosta.mercadolivro.controller.response

import com.anacarolcosta.mercadolivro.enums.CustomerStatus

data class CustomerResponse (
    var id: Int? = null,

    var name: String,

    var email: String,

    var status: CustomerStatus
)
