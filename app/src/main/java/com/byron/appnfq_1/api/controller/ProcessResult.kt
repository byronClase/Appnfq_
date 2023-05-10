package com.byron.appnfq_1.api.controller

interface ProcessResult {
    fun onSuccess()
    fun onError(errorMessage: String)
}
