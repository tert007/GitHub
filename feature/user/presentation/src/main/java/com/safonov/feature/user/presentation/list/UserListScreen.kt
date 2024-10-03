package com.safonov.feature.user.presentation.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.safonov.github.feature.user.domain.model.User
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserListScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: UserListViewModel = koinViewModel()
    var users by remember { mutableStateOf(emptyList<User>()) }

    LaunchedEffect(Unit) {
         viewModel.getUser().getOrNull()?.let {
             users = it
         }
    }

    LazyColumn {
        items(users) { user ->
            Text(text = user.login)
        }
    }
}