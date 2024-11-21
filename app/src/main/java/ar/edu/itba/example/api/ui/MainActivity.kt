package ar.edu.itba.example.api.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ar.edu.itba.example.api.ui.home.HomeScreen
import ar.edu.itba.example.api.ui.theme.APIMutableStateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APIMutableStateTheme {
                HomeScreen()
            }
        }
    }
}