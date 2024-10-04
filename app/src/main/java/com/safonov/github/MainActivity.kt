package com.safonov.github

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.safonov.core.presentation.ui.theme.GitHubUsersTheme
import com.safonov.feature.user.presentation.details.UserDetailsScreen
import com.safonov.feature.user.presentation.list.UserListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubUsersTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.UserList,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable<Screen.UserList> {
                        UserListScreen(
                            modifier = Modifier.fillMaxSize(),
                            onUserDetailsClick = { userId ->
                                navController.navigate(Screen.UserDetails(userId))
                            }
                        )
                    }

                    composable<Screen.UserDetails> { backStackEntry ->
                        val args = backStackEntry.toRoute<Screen.UserDetails>()
                        UserDetailsScreen(
                            userId = args.userId,
                            onBackClick = {
                                navController.popBackStack()
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

