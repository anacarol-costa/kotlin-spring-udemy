package com.anacarolcosta.mercadolivro.service

import org.junit.jupiter.api.Assertions.*

import com.anacarolcosta.mercadolivro.events.PurchaseEvent
import com.anacarolcosta.mercadolivro.helper.buildPurchase
import com.anacarolcosta.mercadolivro.repository.PurchaseRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.ApplicationEventPublisher
import java.util.*

@ExtendWith(MockKExtension::class)
class PurchaseServiceTest {

    @MockK
    private lateinit var purchaseRepository: PurchaseRepository

    @MockK
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @InjectMockKs
    private lateinit var purchaseService: PurchaseService

    val purchaseEventSlot =slot<PurchaseEvent>() //slot criado para verificar evento


    @Test
    fun `deve criar purchase e publicar event`() {
        val purchase =buildPurchase()

        every{purchaseRepository.save(purchase)}returns purchase
        every{applicationEventPublisher.publishEvent(any())} just runs

                purchaseService.create(purchase)

        verify(exactly = 1){purchaseRepository.save(purchase)}
        verify(exactly = 1){applicationEventPublisher.publishEvent(capture(purchaseEventSlot))}

        assertEquals(purchase, purchaseEventSlot.captured.purchaseModel)
    }

    @Test
    fun `deve atualizar purchase`() {
        val purchase =buildPurchase()

        every{purchaseRepository.save(purchase)}returns purchase

        purchaseService.update(purchase)

        verify(exactly = 1){purchaseRepository.save(purchase)}
    }

}