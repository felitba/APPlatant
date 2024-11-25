package ar.edu.itba.example.api.data.repository

import ar.edu.itba.example.api.data.model.Card
import ar.edu.itba.example.api.data.model.WalletDetail
import ar.edu.itba.example.api.data.network.WalletRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class WalletRepository(
    private val remoteDataSource: WalletRemoteDataSource
) {
    val walletDetailStream: Flow<WalletDetail> =
        remoteDataSource.walletDetailStream
            .map { it.asModel() }

    val walletCardsStream: Flow<List<Card>> =
        remoteDataSource.walletCardsStream
            .map { list -> list.map { it.asModel() } }

    suspend fun deposit(amount: Double)  {
       remoteDataSource.deposit(amount)
    }

    private val cardsMutex = Mutex()
    private var cards: List<Card> = emptyList()

    suspend fun addCard(card: Card) : Card {
        val newCard = remoteDataSource.addCard(card.asNetworkModel()).asModel()
        cardsMutex.withLock {
            this.cards = emptyList()
        }
        return newCard
    }

    suspend fun deleteCard(cardId: Int) {
        remoteDataSource.deleteCard(cardId)
        cardsMutex.withLock {
            this.cards = emptyList()
        }
    }
}