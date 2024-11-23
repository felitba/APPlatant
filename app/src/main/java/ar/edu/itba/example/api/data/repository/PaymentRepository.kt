package ar.edu.itba.example.api.data.repository

import ar.edu.itba.example.api.data.model.Payment
import ar.edu.itba.example.api.data.network.PaymentRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class PaymentRepository(
    private val remoteDataSource: PaymentRemoteDataSource
) {
    private val paymentsMutex = Mutex()
    private var cachedPayments: List<Payment> = emptyList()

    val paymentsStream: Flow<List<Payment>> = remoteDataSource.userPaymentsStream.map { networkPayments ->
        networkPayments.map { it.asModel() }
    }

    suspend fun makePayment(payment: Payment) {
        remoteDataSource.makePayment(payment.asNetworkModel())
    }

    suspend fun getUserPayments(refresh: Boolean = false): List<Payment> {
        if (refresh || cachedPayments.isEmpty()) {
            val payments = remoteDataSource.getUserPayments().map { it.asModel() }
            paymentsMutex.withLock {
                cachedPayments = payments
            }
        }
        return paymentsMutex.withLock { cachedPayments }
    }

    suspend fun getPaymentById(paymentId: Int): Payment {
        return remoteDataSource.getPaymentById(paymentId).asModel()
    }

    suspend fun getPaymentLinkDetails(linkUuid: String): Payment {
        return remoteDataSource.getPaymentLinkDetails(linkUuid).asModel()
    }

    suspend fun settlePaymentLink(linkUuid: String) {
        remoteDataSource.settlePaymentLink(linkUuid)
    }
}


