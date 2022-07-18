package com.anacarolcosta.mercadolivro.model

data class CustomerModel (
    var id: String? = null, //permite q a variavel receba valor null
    var name: String,
    var email: String
)//entidade customer do projeto com seus respectivos atributos