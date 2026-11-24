package com.example.cashapp.NetWork

import com.example.cashapp.data.NewUser
import com.example.cashapp.data.UserInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("users")
    suspend fun loadUser(): List<UserInfo>

    @POST("users")
    suspend fun saveUser(
    @Body user: NewUser
    ): UserInfo

    @PUT("users/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Body user: UserInfo
    ): UserInfo
}