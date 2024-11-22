package ar.edu.itba.example.api.data.repository

import ar.edu.itba.example.api.data.model.Card
import ar.edu.itba.example.api.data.model.WalletDetail
import ar.edu.itba.example.api.data.network.WalletRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach
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

    //TODO: add the rest of the card logic.
    // Mutex to make writes to cached values thread-safe.
    private val cardsMutex = Mutex()
    // Cache of the latest sports got from the network.
    private var cards: List<Card> = emptyList()

    suspend fun getCards(refresh: Boolean = false): List<Card> {
        if (refresh || cards.isEmpty()) {
            val result = remoteDataSource.getCards()
            // Thread-safe write to sports
            cardsMutex.withLock {
                this.cards = result.map { it.asModel() }
            }
        }

        return cardsMutex.withLock { this.cards }
    }

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