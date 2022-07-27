package com.anacarolcosta.mercadolivro.model

import com.anacarolcosta.mercadolivro.enums.CustomerStatus
import com.anacarolcosta.mercadolivro.enums.Profile
import javax.persistence.*

@Entity(name = "customer")
data class CustomerModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var email: String,

    @Column
    @Enumerated(EnumType.STRING)
    var status: CustomerStatus,

    @Column
    val password: String,

    @Column(name = "role")
    @CollectionTable(name = "customer_roles", joinColumns = [JoinColumn(name = "customer_id")]) //onde se deve buscar o dado
    @ElementCollection(targetClass = Profile::class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    var roles: Set<Profile> = setOf() //lista q n recebe valores iguais
)//entidade customer do projeto com seus respectivos atributos