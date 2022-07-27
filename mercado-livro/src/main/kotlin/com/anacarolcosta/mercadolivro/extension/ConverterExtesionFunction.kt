package com.anacarolcosta.mercadolivro.extension

import com.anacarolcosta.mercadolivro.controller.request.PostBookRequest
import com.anacarolcosta.mercadolivro.controller.request.PostCustomerRequest
import com.anacarolcosta.mercadolivro.controller.request.PutBookRequest
import com.anacarolcosta.mercadolivro.controller.request.PutCustomerRequest
import com.anacarolcosta.mercadolivro.controller.response.BookResponse
import com.anacarolcosta.mercadolivro.controller.response.CustomerResponse
import com.anacarolcosta.mercadolivro.enums.BookStatus
import com.anacarolcosta.mercadolivro.enums.CustomerStatus
import com.anacarolcosta.mercadolivro.model.BookModel
import com.anacarolcosta.mercadolivro.model.CustomerModel

fun PostCustomerRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(
        name = this.name,
        email = this.email,
        status = CustomerStatus.ATIVO,
        password = this.password
    )
}

fun PutCustomerRequest.toCustomerModel(previousValue: CustomerModel): CustomerModel {
    return CustomerModel(
        id = previousValue.id,
        name = this.name,
        email = this.email,
        status = previousValue.status,
        password = previousValue.password
    )
}

fun PostBookRequest.toBookModel(customer: CustomerModel): BookModel {
    return BookModel(
        name = this.name,
        price = this.price,
        status = BookStatus.ATIVO,
        customer = customer
    )
}

fun PutBookRequest.toBookModel(previousValue: BookModel): BookModel {
    return BookModel(
        id = previousValue.id,
        name = this.name ?: previousValue.name, // se o valor for nulo retornará o previousValue
        price = this.price,
        status = previousValue.status,
        customer = previousValue.customer
    )
}


fun CustomerModel.toResponse(): CustomerResponse {
    return  CustomerResponse(
        id = this.id,
        name = this.name,
        email = this.email,
        status = this.status
    )
}

fun BookModel.toResponse(): BookResponse {
    return BookResponse(
        id = this.id,
        name = this.name,
        price = this.price,
        customer = this.customer,
        status = this.status
    )
}