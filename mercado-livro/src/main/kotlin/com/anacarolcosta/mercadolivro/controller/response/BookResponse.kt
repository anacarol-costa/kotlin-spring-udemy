package com.anacarolcosta.mercadolivro.controller.response

import com.anacarolcosta.mercadolivro.enums.BookStatus
import com.anacarolcosta.mercadolivro.model.CustomerModel
import java.math.BigDecimal

data class BookResponse (
    var id: Int? = null,

    var name: String,

    var price: BigDecimal,

    var customer: CustomerModel? = null,

    var status: BookStatus? = null
)
