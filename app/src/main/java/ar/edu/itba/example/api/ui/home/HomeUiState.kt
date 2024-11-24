package ar.edu.itba.example.api.ui.home

import ar.edu.itba.example.api.data.model.Card
import ar.edu.itba.example.api.data.model.Error
import ar.edu.itba.example.api.data.model.Payment
import ar.edu.itba.example.api.data.model.User
import ar.edu.itba.example.api.data.model.WalletDetail

data class HomeUiState(
    /*User Section */
    val isAuthenticated: Boolean = false,
    val isFetching: Boolean = false,
    val currentUser: User? = null,
    val aboutToLogOut: Boolean = false,

    /*Card+Wallet Section */
    val errorMessage: Boolean = false,
    val walletDetail: WalletDetail? = null,
    val showBalance : Boolean = true,
    val cards: List<Card>? = emptyList(),
    val currentCard: Card? = null,
    val isWriting: Boolean = false,
    val eliminateCardMessage: Boolean = false,
    val isUpdatingCard: Boolean = false,

    /*Payment Section */
    val isPaymentInProgress: Boolean = false,
    val payments: List<Payment>? = emptyList(),

    /*General Errors*/
    val error: Error? = null,
    )

val HomeUiState.canAddCard: Boolean get() = isAuthenticated
val HomeUiState.canDeleteCard: Boolean get() = isAuthenticated && currentCard != null


