package ar.edu.itba.example.api.data.network.api

import ar.edu.itba.example.api.data.network.model.NetworkWalletDetail
import retrofit2.Response
import retrofit2.http.GET

interface WalletApiService {

    @GET("wallet/details")
    suspend fun getDetails(): Response<NetworkWalletDetail>
}