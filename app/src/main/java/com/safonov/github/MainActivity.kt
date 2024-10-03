package com.safonov.github

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.safonov.core.presentation.ui.theme.GitHubUsersTheme
import com.safonov.feature.user.presentation.list.UserListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubUsersTheme {
                UserListScreen(
                    modifier = Modifier.fillMaxSize(),
                    onUserDetailsClick = { userId ->
                        // TODO
                    }
                )
            }
        }
    }
}
