package com.example.emdel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.emdel.chat.presentation.ChatScreen
import com.example.emdel.chat.presentation.ChatViewModel
import com.example.emdel.chat.presentation.ChatViewModelFactory
import com.example.emdel.ui.theme.EmdelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EmdelTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: ChatViewModel = viewModel(
                        factory = ChatViewModelFactory(application)
                    )
                    ChatScreen(viewModel = viewModel)
                }
            }
        }
    }
}