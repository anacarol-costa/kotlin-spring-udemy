package com.anacarolcosta.mercadolivro.repository

import com.anacarolcosta.mercadolivro.model.PurchaseModel
import org.springframework.data.repository.CrudRepository

interface PurchaseRepository: CrudRepository<PurchaseModel, Int>  {

}
