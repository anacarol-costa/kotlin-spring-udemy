package com.anacarolcosta.mercadolivro.helper

import com.anacarolcosta.mercadolivro.enums.CustomerStatus
import com.anacarolcosta.mercadolivro.enums.Role
import com.anacarolcosta.mercadolivro.model.BookModel
import com.anacarolcosta.mercadolivro.model.CustomerModel
import com.anacarolcosta.mercadolivro.model.PurchaseModel
import java.math.BigDecimal
import java.util.*

    fun buildCustomer(
        id: Int? = null,
        name: String = "nome do customer",
        email: String = "${UUID.randomUUID()}@email.com",
        password: String = "senha"
    ) = CustomerModel(
        id = id,
        name = name,
        email = email,
        status = CustomerStatus.ATIVO,
        password = password,
        roles =setOf(Role.CUSTOMER)
    )

    fun buildPurchase(
        id: Int? = null,
        customer: CustomerModel,
        books: MutableList<BookModel> =mutableListOf(),
        nfe: String = UUID.randomUUID().toString(),
        price: BigDecimal = BigDecimal.TEN
    ) = PurchaseModel (
        id = id,
        customer = customer,
        books = books,
        nfe =  nfe,
        price = price
    )
