package ar.edu.itba.example.api.data.network

import ar.edu.itba.example.api.data.network.api.PaymentApiService
import ar.edu.itba.example.api.data.network.model.NetworkPayment
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PaymentRemoteDataSource(
    private val paymentApiService: PaymentApiService
) : RemoteDataSource() {

    suspend fun makePayment(payment: NetworkPayment) {
        handleApiResponse {
            paymentApiService.makePayment(payment)
        }
    }
//
//    suspend fun getUserPayments(
//        page: Int? = 1,
//        direction: String? = "ASC",
//        pending: Boolean? = null
//    ): List<NetworkPayment> {
//        return handleApiResponse {
//            paymentApiService.getUserPayments(page, direction, pending)
//        }
//    }

    suspend fun getPaymentById(paymentId: Int): NetworkPayment {
        return handleApiResponse {
            paymentApiService.getPaymentById(paymentId)
        }
    }

    suspend fun getPaymentLinkDetails(linkUuid: String): NetworkPayment {
        return handleApiResponse {
            paymentApiService.getPaymentLinkDetails(linkUuid)
        }
    }

    suspend fun settlePaymentLink(linkUuid: String) {
        handleApiResponse {
            paymentApiService.settlePaymentLink(linkUuid)
        }
    }

    val userPaymentsStream: Flow<List<NetworkPayment>> = flow {
        while (true) {
            val payments = handleApiResponse {
                paymentApiService.getUserPayments()

            }
            emit(payments)
            delay(DELAY)
        }
    }

    companion object {
        const val DELAY: Long = 5000
    }
}
