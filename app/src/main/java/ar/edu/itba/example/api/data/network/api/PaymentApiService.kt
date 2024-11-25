package ar.edu.itba.example.api.data.network.api

import ar.edu.itba.example.api.data.network.model.NetworkPayment
import retrofit2.Response
import retrofit2.http.*

interface PaymentApiService {

    @POST("payment")
    suspend fun makePayment(@Body payment: NetworkPayment): Response<Unit>

    @GET("payment")
    suspend fun getUserPayments(
//        @Query("page") page: Int? = 1,
//        @Query("direction") direction: String? = "ASC",
//        @Query("pending") pending: Boolean? = false,
//        @Query("type") type: String? = "BALANCE",
    ): Response<List<NetworkPayment>>

    @GET("payment/{paymentId}")
    suspend fun getPaymentById(@Path("paymentId") paymentId: Int): Response<NetworkPayment>

    @GET("payment/link/{linkUuid}")
    suspend fun getPaymentLinkDetails(@Path("linkUuid") linkUuid: String): Response<NetworkPayment>

    @POST("payment/link/{linkUuid}")
    suspend fun settlePaymentLink(@Path("linkUuid") linkUuid: String): Response<Unit>
}
