package com.anacarolcosta.mercadolivro.service

import com.anacarolcosta.mercadolivro.enums.BookStatus
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

    fun findAll(): List<BookModel> {
        return bookRepository.findAll().toList()
    }

    fun findActives(): List<BookModel> {
        return bookRepository.findByStatus(BookStatus.ATIVO)
    }

    fun findById(id: Int): BookModel {
        return  bookRepository.findById(id).orElseThrow()
    }

    fun delete(id: Int) {
        val book = findById(id)

        book.status = BookStatus.CANCELADO

        update(book)
    } // não irá deletar, pois o livro terá status

    fun update(book: BookModel) {
        bookRepository.save(book)
    }
}
