package ar.edu.itba.example.api.data.network.api

import ar.edu.itba.example.api.data.network.model.NetworkCode
import ar.edu.itba.example.api.data.network.model.NetworkCredentials
import ar.edu.itba.example.api.data.network.model.NetworkRegisterUser
import ar.edu.itba.example.api.data.network.model.NetworkToken
import ar.edu.itba.example.api.data.network.model.NetworkUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiService {
    @POST("user/login")
    suspend fun login(@Body credentials: NetworkCredentials): Response<NetworkToken>

    @POST("user/logout")
    suspend fun logout(): Response<Unit>

    @GET("user")
    suspend fun getCurrentUser(): Response<NetworkUser>

    @POST("user")
    suspend fun registerUser(@Body user: NetworkRegisterUser): Response<NetworkUser>

    @POST("user/verify")
    suspend fun verify(@Body code: NetworkCode): Response<NetworkUser>
}