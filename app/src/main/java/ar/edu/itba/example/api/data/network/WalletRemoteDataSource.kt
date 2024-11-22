package ar.edu.itba.example.api.data.network

import ar.edu.itba.example.api.data.network.api.WalletApiService
import ar.edu.itba.example.api.data.network.model.NetworkAmount
import ar.edu.itba.example.api.data.network.model.NetworkCard
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

    //Testear esta funcion.
//    val walletCardsStream: Flow<NetworkCard> = flow {
//        while (true) {
//            val walletCards = handleApiResponse {
//                walletApiService.getCards()
//            }
//            emit(walletCards)
//            delay(DELAY)
//        }
//    }

    suspend fun getCards() = handleApiResponse {
        walletApiService.getCards()
    }

    suspend fun addCard(card: NetworkCard) = handleApiResponse {
        walletApiService.addCard(card)
    }

    suspend fun deleteCard(cardId: Int) = handleApiResponse {
        walletApiService.deleteCard(cardId)
    }



    companion object {
        const val DELAY: Long = 10000
    }
}