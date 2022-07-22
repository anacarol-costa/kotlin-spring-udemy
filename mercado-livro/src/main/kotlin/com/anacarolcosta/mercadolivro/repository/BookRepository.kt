package com.anacarolcosta.mercadolivro.repository

import com.anacarolcosta.mercadolivro.enums.BookStatus
import com.anacarolcosta.mercadolivro.model.BookModel
import com.anacarolcosta.mercadolivro.model.CustomerModel
import org.springframework.data.repository.CrudRepository

interface BookRepository: CrudRepository<BookModel, Int> {
    fun findByStatus(status: BookStatus): List<BookModel>
}