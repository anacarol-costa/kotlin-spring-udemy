package com.anacarolcosta.mercadolivro.events.listener

import com.anacarolcosta.mercadolivro.events.PurchaseEvent
import com.anacarolcosta.mercadolivro.service.BookService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UpdateSoldBookListener(
    private val bookService: BookService
) {

    @EventListener
    fun listen(purchaseEvent: PurchaseEvent) {
        bookService.purchase(purchaseEvent.purchaseModel.books)
    }
}