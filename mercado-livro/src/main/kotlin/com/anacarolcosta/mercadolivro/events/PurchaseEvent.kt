package com.anacarolcosta.mercadolivro.events

import com.anacarolcosta.mercadolivro.model.PurchaseModel
import org.springframework.context.ApplicationEvent

class PurchaseEvent(
    source: Any,//quem dispara o evento
    val purchaseModel: PurchaseModel
): ApplicationEvent(source)