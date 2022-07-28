package com.anacarolcosta.mercadolivro.security

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.FUNCTION) //metodo vai ficar em cima de uma função
@Retention(AnnotationRetention.RUNTIME) //existe apenas em tempo de execução
@PreAuthorize("hasRole('ROLE_ADMIN') || #id == authentication.principal.id") // autorização para admin e para o dono do recurso
annotation class UserCanOnlyAcessTheirOwnResource
