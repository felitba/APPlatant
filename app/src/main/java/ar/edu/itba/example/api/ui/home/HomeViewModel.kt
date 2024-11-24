package ar.edu.itba.example.api.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ar.edu.itba.example.api.MyApplication
import ar.edu.itba.example.api.data.DataSourceException
import ar.edu.itba.example.api.data.model.Error
import ar.edu.itba.example.api.data.repository.UserRepository
import ar.edu.itba.example.api.data.repository.WalletRepository
import ar.edu.itba.example.api.SessionManager
import ar.edu.itba.example.api.data.model.Card
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val walletRepository: WalletRepository
) : ViewModel() {

    private var walletDetailStreamJob: Job? = null
    private var walletCardsStreamJob: Job? = null
    private val _uiState = MutableStateFlow(HomeUiState(isAuthenticated = sessionManager.loadAuthToken() != null))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        if (uiState.value.isAuthenticated) {
            observeWalletDetailStream()
            observeWalletCardsStream()
        }
    }

    /* Login Section */

    fun login(username: String, password: String) = runOnViewModelScope(
        {
            userRepository.login(username, password)
            observeWalletDetailStream()
            observeWalletCardsStream()
        },
        { state, _ -> state.copy(isAuthenticated = true) }
    )

    fun logout() = runOnViewModelScope(
        {
            walletDetailStreamJob?.cancel()
            userRepository.logout()
        },
        { state, _ ->
            state.copy(
                isAuthenticated = false,
                walletDetail = null
            )
        }
    )

//    fun getCurrentUser() = runOnViewModelScope(
//        { userRepository.getCurrentUser(uiState.currentUser == null) },
//        { state, response -> state.copy(currentUser = response) }
//    )

/* Deposit Section */
    fun deposit(amount: Double) = runOnViewModelScope(
        {
            walletRepository.deposit(amount)
        },
        //TODO: ver si hay que cambiar el state aca.
        { state, _ ->
            state.copy(
            )
        }
    )

    fun changeBalanceView() = runOnViewModelScope(
        {
            _uiState.update { currentState -> currentState.copy(showBalance = !currentState.showBalance) }
        },
        { state, _ -> state }
    )

    fun changeIsWritingAmount() = runOnViewModelScope(
        {
            _uiState.update { currentState -> currentState.copy(isWriting = !currentState.isWriting) }
        },
        { state, _ -> state }
    )

    /* Cards Section */

//    fun getCards() = runOnViewModelScope(
//        {
//            walletRepository.getCards()
//        },
//        { state, response -> state.copy(cards = response) }
//    )

    fun addCard(card: Card) = runOnViewModelScope(
        {
            walletRepository.addCard(card)
        },
        { state, response ->
            state.copy(
                currentCard = response,
                cards = null
            )
        }
    )

    fun deleteCard(cardId: Int) = runOnViewModelScope(
        { walletRepository.deleteCard(cardId) },
        { state, _ ->
            state.copy(
                currentCard = null,
                cards = null
            )
        }
    )

    fun setCurrentCard(card: Card) = runOnViewModelScope(
        {
            _uiState.update { currentState -> currentState.copy(currentCard = card) }
        },
        { state, _ -> state }
    )

    fun changeErrorMessage() = runOnViewModelScope(
        {
            _uiState.update { currentState -> currentState.copy(errorMessage = !currentState.errorMessage) }
        },
        { state, _ -> state }
    )

    private fun observeWalletDetailStream() {
        walletDetailStreamJob = collectOnViewModelScope(
            walletRepository.walletDetailStream
        ) { state, response -> state.copy(walletDetail = response) }
    }

    private fun observeWalletCardsStream() {
        walletCardsStreamJob = collectOnViewModelScope(
            walletRepository.walletCardsStream
        ) { state, response -> state.copy(cards = response) }
    }

    private fun <T> collectOnViewModelScope(
        flow: Flow<T>,
        updateState: (HomeUiState, T) -> HomeUiState
    ) = viewModelScope.launch {
        flow
            .distinctUntilChanged()
            .catch { e -> _uiState.update { currentState -> currentState.copy(error = handleError(e)) } }
            .collect { response -> _uiState.update { currentState -> updateState(currentState, response) } }
    }

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (HomeUiState, R) -> HomeUiState
    ): Job = viewModelScope.launch {
        _uiState.update { currentState -> currentState.copy(isFetching = true, error = null) }
        runCatching {
            block()
        }.onSuccess { response ->
            _uiState.update { currentState -> updateState(currentState, response).copy(isFetching = false) }
        }.onFailure { e ->
            _uiState.update { currentState -> currentState.copy(isFetching = false, error = handleError(e)) }
            Log.e(TAG, "Coroutine execution failed", e)
        }
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.code, e.message ?: "")
        } else {
            Error(null, e.message ?: "")
        }
    }

    companion object {
        const val TAG = "UI Layer"

        fun provideFactory(
            application: MyApplication
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(
                    application.sessionManager,
                    application.userRepository,
                    application.walletRepository) as T
            }
        }
    }
}