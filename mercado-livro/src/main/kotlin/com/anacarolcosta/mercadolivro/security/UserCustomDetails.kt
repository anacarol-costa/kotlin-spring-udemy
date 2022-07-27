package com.anacarolcosta.mercadolivro.security

import com.anacarolcosta.mercadolivro.enums.CustomerStatus
import com.anacarolcosta.mercadolivro.model.CustomerModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserCustomDetails(val customerModel: CustomerModel): UserDetails {
    val id = customerModel.id

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = customerModel.roles.map {
        SimpleGrantedAuthority(it.description)
    }.toMutableList() //criar objetos a partir das roles

    override fun getPassword(): String = customerModel.password

    override fun getUsername(): String = customerModel.id.toString()

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = customerModel.status == CustomerStatus.ATIVO //usuário está ativo?
}