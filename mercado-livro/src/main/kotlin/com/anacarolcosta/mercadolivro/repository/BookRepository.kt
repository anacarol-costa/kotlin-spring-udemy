package com.anacarolcosta.mercadolivro.repository

import com.anacarolcosta.mercadolivro.model.BookModel
import com.anacarolcosta.mercadolivro.model.CustomerModel
import org.springframework.data.repository.CrudRepository

interface BookRepository: CrudRepository<BookModel, Int> {

}