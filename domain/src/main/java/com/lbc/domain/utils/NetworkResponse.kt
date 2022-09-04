package com.lbc.domain.utils

data class NetworkResponse<T>(
    val networkStatus: NetworkStatus,
    val data: T? = null,
    val errorMessage: String? = null
)