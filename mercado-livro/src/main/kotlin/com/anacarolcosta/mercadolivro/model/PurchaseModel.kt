package com.anacarolcosta.mercadolivro.model

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.*

@Entity(name = "purchase")
data class PurchaseModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @ManyToOne    //muitas compras para um customer
    @JoinColumn(name = "customer_id")
    val customer: CustomerModel,

    @ManyToMany //uma compra pode ter varios livros e este mesmo livros pode está em varias compras
    @JoinTable(name = "purchase_book",
        joinColumns = [JoinColumn(name = "purchase_id")],
        inverseJoinColumns = [JoinColumn(name = "book_id")]) //interseção entre compras e livros
    val books: List<BookModel>,

    @Column
    val nfe: String? = null, //nota fiscal eletronica será gerada dps

    @Column
    val price: BigDecimal,

    @Column
    val createdAt: LocalDateTime = LocalDateTime.now() //indica qdo a compra foi realizada
)