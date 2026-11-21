package com.example.cashapp.NetWork

import com.example.cashapp.data.NewUser
import com.example.cashapp.data.UserInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("users")
    suspend fun loadUser(): List<UserInfo>

    @POST("users")
    suspend fun saveUser(
    @Body user: NewUser
    ): UserInfo
}