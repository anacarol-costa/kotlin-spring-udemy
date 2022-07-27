package com.anacarolcosta.mercadolivro.service

import com.anacarolcosta.mercadolivro.exception.AuthenticationException
import com.anacarolcosta.mercadolivro.repository.CustomerRepository
import com.anacarolcosta.mercadolivro.security.UserCustomDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsCustomService(
    private val customerRepository: CustomerRepository
) : UserDetailsService {

    override fun loadUserByUsername(id: String): UserDetails {
        val customer = customerRepository.findById(id.toInt())
            .orElseThrow { AuthenticationException("Usuário não encontrado!", "999") }

        return UserCustomDetails(customer)
    }
}