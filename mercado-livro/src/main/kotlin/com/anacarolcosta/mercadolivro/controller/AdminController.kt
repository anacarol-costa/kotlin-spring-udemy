package com.anacarolcosta.mercadolivro.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("admins") //pasta ou caminho do endpoint
class AdminController () {

    @GetMapping("/reports")
    fun report(): String {
       return "This is a report only admin can see it!"
    }

}