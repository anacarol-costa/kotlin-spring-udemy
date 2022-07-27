package com.anacarolcosta.mercadolivro.events.listener

import com.anacarolcosta.mercadolivro.events.PurchaseEvent
import com.anacarolcosta.mercadolivro.service.PurchaseService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class GenerateNfeListener(
    private val purchaseService: PurchaseService
) {

    @EventListener
    fun listen(purchaseEvent: PurchaseEvent) {
        val nfe = UUID.randomUUID().toString() //vai gerar automaticamente e aleatoriamente um "id"
        val purchaseModel = purchaseEvent.purchaseModel.copy(nfe = nfe)
        purchaseService.update(purchaseModel)
    }
}