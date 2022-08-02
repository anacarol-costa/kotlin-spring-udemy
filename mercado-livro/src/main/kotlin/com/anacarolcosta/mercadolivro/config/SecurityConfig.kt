package com.anacarolcosta.mercadolivro.config

import com.anacarolcosta.mercadolivro.enums.Role
import com.anacarolcosta.mercadolivro.repository.CustomerRepository
import com.anacarolcosta.mercadolivro.security.AuthenticationFilter
import com.anacarolcosta.mercadolivro.security.AuthorizationFilter
import com.anacarolcosta.mercadolivro.security.CustomAuthenticationEntryPoint
import com.anacarolcosta.mercadolivro.security.JwtUtil
import com.anacarolcosta.mercadolivro.service.UserDetailsCustomService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //validação para o id apenas retorno dos seus recursos (getById). Autorizaçãp do preauthorize
class SecurityConfig(
    private val customerRepository: CustomerRepository,
    private val userDetails : UserDetailsCustomService,
    private val jwtUtil: JwtUtil,
    private val customEntryPoint: CustomAuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {

    private val PUBLIC_MATCHERS = arrayOf<String>()//url aberta

    private val PUBLIC_POST_MATCHERS = arrayOf(
        "/customers"
    ) //rotas publicas

    private val ADMIN_MATCHERS = arrayOf(
        "/admins/**"
    ) //todas as rotas q começam com admins

    private val PUBLIC_GET_MATCHERS = arrayOf(
        "/books"
    )

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetails).passwordEncoder(bCryptPasswordEncoder())
    } //autentica

    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()
        http.authorizeHttpRequests()
            .antMatchers(*PUBLIC_MATCHERS).permitAll()
            .antMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll()
            .antMatchers(*ADMIN_MATCHERS).hasAnyAuthority((Role.ADMIN.description))
            .antMatchers(HttpMethod.GET, *PUBLIC_GET_MATCHERS).permitAll()
            .anyRequest().authenticated()//requests tem q está autenticadas
        http.addFilter(AuthenticationFilter(authenticationManager(), customerRepository, jwtUtil))//filtro de autenticação
        http.addFilter(AuthorizationFilter(authenticationManager(), userDetails, jwtUtil))
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//requisições independentes
        http.exceptionHandling().authenticationEntryPoint(customEntryPoint) //tratar exceptions
    } //recebe as info

    override fun configure(web: WebSecurity) {
        web.ignoring()
            .antMatchers(
                "/v2/api-docs",
                "/v3/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui/**",
                "/webjars/**",
                "/csrf/**"
            )
    } //liberar o swagger, mas apareceu acesso negado usando a rota http://localhost:8080/swagger-ui.html.
    // A rota http://localhost:8080/swagger-ui/ funciona


    @Bean
    fun corsConfig(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()

        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)

        return CorsFilter(source)
    } //configurar o cors

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}