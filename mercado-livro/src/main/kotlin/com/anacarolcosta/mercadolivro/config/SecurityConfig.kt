package com.anacarolcosta.mercadolivro.config

import com.anacarolcosta.mercadolivro.repository.CustomerRepository
import com.anacarolcosta.mercadolivro.security.AuthenticationFilter
import com.anacarolcosta.mercadolivro.security.JwtUtil
import com.anacarolcosta.mercadolivro.service.UserDetailsCustomService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customerRepository: CustomerRepository,
    private val userDetails : UserDetailsCustomService,
    private val jwtUtil: JwtUtil
) : WebSecurityConfigurerAdapter() {

    private val PUBLIC_MATCHERS = arrayOf<String>()//url aberta

    private val PUBLIC_POST_MATCHERS = arrayOf(
        "/customer"
    ) //rotas publicas

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetails).passwordEncoder(bCryptPasswordEncoder())
    } //autentica
    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()
        http.authorizeHttpRequests()
            .antMatchers(*PUBLIC_MATCHERS).permitAll()
            .antMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll()
            .anyRequest().authenticated()//requests tem q está autenticadas
        http.addFilter(AuthenticationFilter(authenticationManager(), customerRepository, jwtUtil))//filtro de autenticação
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//requisições independentes
    } //recebe as info

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}