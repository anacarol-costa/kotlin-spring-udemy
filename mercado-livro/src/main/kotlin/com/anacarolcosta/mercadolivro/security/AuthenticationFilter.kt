package com.anacarolcosta.mercadolivro.security

import com.anacarolcosta.mercadolivro.controller.request.LoginRequest
import com.anacarolcosta.mercadolivro.exception.AuthenticationException
import com.anacarolcosta.mercadolivro.repository.CustomerRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val customerRepository: CustomerRepository
) : UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {

        try{
            val loginRequest = jacksonObjectMapper().readValue(
                request.inputStream,
                LoginRequest::class.java
            )// transforma oq vier no corpo da requisição em objeto de login request

            val id = customerRepository.findByEmail(loginRequest.email)?.id // recupera id

            val authToken = UsernamePasswordAuthenticationToken(id, loginRequest.password) // recebe id do customer e senha

            return authenticationManager.authenticate((authToken)) //spring autentica
        } catch (ex: Exception) {
            throw AuthenticationException("Falha ao autenticar!", "999")
        }
    } //tentativa de autenticação

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val id = (authResult.principal as UserCustomDetails).id

        response.addHeader("Authorization","123456" )
    } //gera o token
}