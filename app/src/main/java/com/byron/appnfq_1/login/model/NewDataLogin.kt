package com.byron.appnfq_1.login.model

data class NewDataLogin(
    override val success: Boolean,
    val token: String,
    override val message: String
) : NewDataResponse
