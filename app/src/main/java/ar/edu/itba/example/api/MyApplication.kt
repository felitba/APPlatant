package ar.edu.itba.example.api

import android.app.Application
import ar.edu.itba.example.api.data.network.PaymentRemoteDataSource
import ar.edu.itba.example.api.data.network.UserRemoteDataSource
import ar.edu.itba.example.api.data.network.WalletRemoteDataSource
import ar.edu.itba.example.api.data.network.api.RetrofitClient
import ar.edu.itba.example.api.data.repository.PaymentRepository
import ar.edu.itba.example.api.data.repository.UserRepository
import ar.edu.itba.example.api.data.repository.WalletRepository

class MyApplication : Application() {

    private val userRemoteDataSource: UserRemoteDataSource
        get() = UserRemoteDataSource(sessionManager, RetrofitClient.getUserApiService(this))

    private val walletRemoteDataSource: WalletRemoteDataSource
        get() = WalletRemoteDataSource(RetrofitClient.getWalletApiService(this))

    private val paymentsRemoteDataSource: PaymentRemoteDataSource
        get() = PaymentRemoteDataSource(RetrofitClient.getPaymentApiService(this))

    val sessionManager: SessionManager
        get() = SessionManager(this)

    val userRepository: UserRepository
        get() = UserRepository(userRemoteDataSource)

    val walletRepository: WalletRepository
        get() = WalletRepository(walletRemoteDataSource)

    val paymentRepository: PaymentRepository
        get() = PaymentRepository(paymentsRemoteDataSource)
}