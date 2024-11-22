package ar.edu.itba.example.api.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.itba.example.api.MyApplication
import ar.edu.itba.example.api.R
import ar.edu.itba.example.api.data.model.Card
import ar.edu.itba.example.api.data.model.CardType
import ar.edu.itba.example.api.ui.components.ActionButton
import kotlin.random.Random

@Composable
fun CardsScreen(viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    val uiState by viewModel.uiState.collectAsState()

    Column{
        ActionButton(
            resId = R.string.get_all_cards,
            enabled = uiState.canGetAllCards,
            onClick = {
                viewModel.getCards()
            })
        ActionButton(
            resId = R.string.add_card,
            enabled = uiState.canAddCard,
            onClick = {
                val random = Random.nextInt(0, 9999)
                val card = Card(number = "499003140861${random.toString().padStart(4, '0')}",
                    fullName = "Christeen Mischke",
                    expirationDate = "05/28",
                    cvv = "215",
                    type = CardType.CREDIT)
                viewModel.addCard(card)
            })
        ActionButton(
            resId = R.string.delete_card,
            enabled = uiState.canDeleteCard,
            onClick = {
                val currentCard = uiState.currentCard!!
                viewModel.deleteCard(currentCard.id!!)
            })
    }

    if (uiState.error != null) {
        Text(
            text = "${uiState.error!!.code} - ${uiState.error!!.message}",
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            fontSize = 18.sp
        )
    }
}