package com.anacarolcosta.mercadolivro.controller.response

class PageResponse<T> (
    var items: List<T>,
    var currentPage: Int,
    var totalItems: Long,
    var totalPages: Int,
) //tributo a serem retornados na paginação

//<T> é um genérico