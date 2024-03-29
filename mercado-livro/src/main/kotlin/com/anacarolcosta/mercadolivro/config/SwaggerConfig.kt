package com.anacarolcosta.mercadolivro.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

//onde será criado o bean

@Profile("!prod") //só irá rodar se o ambiente for diferente de prod
@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.anacarolcosta.mercadolivro.controller"))
            .paths(PathSelectors.any()) //passa os paths. Nesse caso, todos
            .build() //retorna um docket

            .apiInfo(ApiInfoBuilder()
                .title("Mercado Livro")
                .description("Api do Mercado Livro")
                .build())
    }
}