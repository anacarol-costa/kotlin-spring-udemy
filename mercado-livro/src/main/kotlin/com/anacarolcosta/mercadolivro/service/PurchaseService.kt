package com.anacarolcosta.mercadolivro.service

import com.anacarolcosta.mercadolivro.events.PurchaseEvent
import com.anacarolcosta.mercadolivro.model.PurchaseModel
import com.anacarolcosta.mercadolivro.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val purchaseRepository: PurchaseRepository,
    private val applicationEventPublisher: ApplicationEventPublisher //dispara o evento
) {

    fun create( purchaseModel: PurchaseModel) {
        purchaseRepository.save(purchaseModel)

        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchaseModel))
    }

    fun update(purchaseModel: PurchaseModel) {
        purchaseRepository.save(purchaseModel)
    }
}
