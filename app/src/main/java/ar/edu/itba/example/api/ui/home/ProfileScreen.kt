package ar.edu.itba.example.api.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.itba.example.api.R

@Composable
fun ProfileScreen(viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    uiState.currentUser ?: return
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Picture
        Image(
            painter = painterResource(id = R.drawable.perfil),
            contentDescription = stringResource(id = R.string.profile_picture),
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // User Information
        Text(
            text = (uiState.currentUser?.firstName + " " + uiState.currentUser?.lastName),
            fontSize = 24.sp,
            color = Color.Black
        )
        uiState.currentUser?.email?.let {
            Text(
                text = it,
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Actions
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { /* Edit Profile Action */ }) {
                Text(text = stringResource(id = R.string.edit_profile))
            }
            Button(onClick = { viewModel.logout() }) {
                Text(text = stringResource(id = R.string.logout))
            }
        }
    }
}