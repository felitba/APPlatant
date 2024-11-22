package ar.edu.itba.example.api.data.network

import ar.edu.itba.example.api.data.network.api.WalletApiService
import ar.edu.itba.example.api.data.network.model.NetworkAmount
import ar.edu.itba.example.api.data.network.model.NetworkWalletDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WalletRemoteDataSource(
    private val walletApiService: WalletApiService
) : RemoteDataSource() {

    val walletDetailStream: Flow<NetworkWalletDetail> = flow {
        while (true) {
            val walletDetail = handleApiResponse {
                walletApiService.getDetails()
            }
            emit(walletDetail)
            delay(DELAY)
        }
    }

    suspend fun deposit(amount: Double) {
        handleApiResponse {
            walletApiService.deposit(NetworkAmount(amount))
        }
    }

    companion object {
        const val DELAY: Long = 10000
    }
}