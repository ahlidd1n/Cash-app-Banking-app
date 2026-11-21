package com.example.cashapp.data

data class UserInfo(
    val fullName: String = "User",
    val email: String = "unknown",
    val phone: Int = 0,
    val password: String = "******",
    val cardNumber: Int = 0,
    val balance: Double = 0.00
)