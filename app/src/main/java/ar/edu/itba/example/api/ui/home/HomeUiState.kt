package ar.edu.itba.example.api.ui.home

import ar.edu.itba.example.api.data.model.Card
import ar.edu.itba.example.api.data.model.Error
import ar.edu.itba.example.api.data.model.User
import ar.edu.itba.example.api.data.model.WalletDetail

data class HomeUiState(
    val isAuthenticated: Boolean = false,
    val isFetching: Boolean = false,
    val currentUser: User? = null,
    val walletDetail: WalletDetail? = null,
    val error: Error? = null,
    val showBalance : Boolean = true,
    val cards: List<Card>? = emptyList(),
    val currentCard: Card? = null,
    val isWriting: Boolean = false,
    val errorMessage: Boolean = false,
    val aboutToLogOut: Boolean = false,
)

//val HomeUiState.canGetCurrentUser: Boolean get() = isAuthenticated
//val HomeUiState.canGetAllCards: Boolean get() = isAuthenticated
val HomeUiState.canAddCard: Boolean get() = isAuthenticated
val HomeUiState.canDeleteCard: Boolean get() = isAuthenticated && currentCard != null
