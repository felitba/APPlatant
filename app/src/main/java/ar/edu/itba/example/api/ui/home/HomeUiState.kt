package ar.edu.itba.example.api.ui.home

import ar.edu.itba.example.api.data.model.Error
import ar.edu.itba.example.api.data.model.WalletDetail

data class HomeUiState(
    val isAuthenticated: Boolean = false,
    val isFetching: Boolean = false,
    val walletDetail: WalletDetail? = null,
    val error: Error? = null,
    val showBalance : Boolean = true
)
