package com.anacarolcosta.mercadolivro.controller.request

import com.sun.istack.NotNull
import javax.validation.constraints.Positive

data class PostPurchaseRequest (

    @field:NotNull
    @field:Positive //numero maior que zero
    val customerId: Int,

    @field:NotNull
    val bookIds: Set<Int> //recebe valores diferentes
)
