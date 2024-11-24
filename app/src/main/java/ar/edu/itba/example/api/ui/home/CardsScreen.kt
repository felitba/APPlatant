package ar.edu.itba.example.api.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.itba.example.api.R
import ar.edu.itba.example.api.data.model.Card
import ar.edu.itba.example.api.data.model.CardType
import ar.edu.itba.example.api.ui.components.ActionButton
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import ar.edu.itba.example.api.ui.components.AdaptiveCard
import kotlin.random.Random

@Composable
fun CardsScreen(
    viewModel: HomeViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val lazyGridState = rememberLazyGridState()
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp > 600
    val gridColumns = if (isTablet) GridCells.Fixed(2) else GridCells.Fixed(1)


    Box(
        modifier = Modifier
                    .fillMaxSize()
                     .padding(16.dp)
                    .background(color = colorScheme.background)
        ,
    ) {
        //Title Section.
        Column(
            modifier = Modifier
                .background(color = colorScheme.onBackground)
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(uiState.cards != null){
            Text(
                text = stringResource(id = R.string.cards),
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                color = colorScheme.secondary,
                fontSize = 36.sp,
            )
                }
            Column(
                modifier = Modifier
                    .background(color = colorScheme.background)
                    .padding(top = 24.dp, bottom = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                if (uiState.cards != null) {

                    val mediumPadding = dimensionResource(R.dimen.medium_padding)

                    LazyVerticalGrid(
                        contentPadding = PaddingValues(
                            horizontal = mediumPadding,
                            vertical = mediumPadding,
                        ),
                        verticalArrangement = Arrangement.spacedBy(
                            16.dp,
                            Alignment.CenterVertically
                        ),
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier,
                        columns = gridColumns,
                        userScrollEnabled = true,
                        state = lazyGridState,
                    )
                    {
                        when {
                            //NOTE: Ignorar este warning. Si que puede ser NULL: cuando esta fetcheando datos.
                            uiState.cards == null -> {
                                item {
                                    Text(text= stringResource(id = R.string.loading), Modifier.padding(16.dp))
                                }
                            }

                            uiState.cards!!.isEmpty() -> {
//                                item {
//                                }
                            }

                            else -> {
                                items(items = uiState.cards!!) { item ->
                                    AdaptiveCard(item,
                                        isSelected = uiState.currentCard?.id == item.id,
                                        onClick = { viewModel.setCurrentCard(it) })
                                }
                            }
                        }
                        item {
                            Text(
                                text = "${stringResource(R.string.total_cards)} ${uiState.cards!!.size}",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                fontSize = typography.bodyMedium.fontSize
                            )
                        }
                    }

                } else {
                    Text(text= stringResource(id = R.string.loading), Modifier.padding(16.dp))
                }
            }
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
        if (uiState.cards != null) {
            Column(
                modifier = Modifier.align(Alignment.BottomEnd),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // NOTE: This add button is only for testing purposes.
                ActionButton(
                    resId = R.string.add_card,
                    enabled = uiState.canAddCard,
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(id = R.string.add_card),
                            modifier = Modifier
                                .padding(
                                    top = 16.dp, start = 16.dp, end = 16.dp
                                ),
                        )
                    },
                    onClick = {
                        val random = Random.nextInt(0, 9999)
                        viewModel.getCurrentUser()
                        val card = Card(
                            number = "499003140861${random.toString().padStart(4, '0')}",
                            fullName = "${uiState.currentUser!!.firstName} ${uiState.currentUser!!.lastName}",
                            expirationDate = "05/28",
                            cvv = "215",
                            type = CardType.CREDIT
                        )
                        viewModel.addCard(card)
                    })
                ActionButton(
                    resId = R.string.delete_card,
                    enabled = uiState.canDeleteCard,
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(id = R.string.delete_card),
                            modifier = Modifier
                                .padding(
                                    top = 16.dp, start = 16.dp, end = 16.dp
                                ),
                        )
                    },
                    onClick = {
                        viewModel.displayEliminateCardMessage()
                    })

                if (uiState.eliminateCardMessage) {
                    val currentCard = uiState.currentCard!!

                    Dialog(onDismissRequest = { viewModel.displayEliminateCardMessage() }) {
                        Surface(
                            shape = shapes.medium,
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(id = R.string.confirm_eliminate_card) + " : " + uiState.currentCard!!.number,
                                    style = typography.bodyMedium,
                                    modifier = Modifier.padding(16.dp),
                                    fontSize = 24.sp,
                                )
                                Row {
                                    Button(
                                        onClick = { viewModel.displayEliminateCardMessage() },
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    ) {
                                        Text(
                                            stringResource(id = R.string.cancel),
                                        )
                                    }
                                    Button(onClick = {
                                        viewModel.displayEliminateCardMessage()
                                        viewModel.deleteCard(currentCard.id!!)
                                    }) {
                                        Text(
                                            stringResource(id = R.string.confirm),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}