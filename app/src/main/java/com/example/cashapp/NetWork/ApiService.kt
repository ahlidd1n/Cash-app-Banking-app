package com.example.cashapp.NetWork

import com.example.cashapp.data.UserInfo
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun loadUser(): List<UserInfo>
}