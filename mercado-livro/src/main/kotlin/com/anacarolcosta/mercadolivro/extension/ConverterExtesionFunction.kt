package com.anacarolcosta.mercadolivro.extension

import com.anacarolcosta.mercadolivro.controller.request.PostBookRequest
import com.anacarolcosta.mercadolivro.controller.request.PostCustomerRequest
import com.anacarolcosta.mercadolivro.controller.request.PutCustomerRequest
import com.anacarolcosta.mercadolivro.enums.BookStatus
import com.anacarolcosta.mercadolivro.model.BookModel
import com.anacarolcosta.mercadolivro.model.CustomerModel

fun PostCustomerRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(name = this.name, email = this.email)
}

fun PutCustomerRequest.toCustomerModel(id: Int): CustomerModel {
    return CustomerModel(id = id, name = this.name, email = this.email)
}

fun PostBookRequest.toBookModel(customer: CustomerModel): BookModel {
    return BookModel(
        name = this.name,
        price = this.price,
        status = BookStatus.ATIVO,
        customer = customer
    )
}