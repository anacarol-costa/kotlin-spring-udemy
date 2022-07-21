package com.anacarolcosta.mercadolivro.service

import com.anacarolcosta.mercadolivro.model.BookModel
import com.anacarolcosta.mercadolivro.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {
    fun create(book: BookModel) {
        bookRepository.save(book)
    }

}
