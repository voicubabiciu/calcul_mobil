package com.ancafarcas.proiectandroid.common

import java.lang.Exception

data class BusinessException(override val message: String) : Exception(message)