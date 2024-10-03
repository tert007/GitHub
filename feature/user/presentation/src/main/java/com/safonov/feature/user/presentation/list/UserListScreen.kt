package com.safonov.feature.user.presentation.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.safonov.feature.user.presentation.list.mvi.UserListIntent
import com.safonov.feature.user.presentation.list.mvi.UserListUiState
import com.safonov.feature.user.presentation.list.mvi.UserListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    modifier: Modifier = Modifier,
    onUserDetailsClick: (userId: Int) -> Unit,
) {
    val viewModel: UserListViewModel = koinViewModel()
    val state: UserListUiState by viewModel.uiState.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    state.message?.let { message ->
        viewModel.onIntentTriggered(UserListIntent.ConsumedMessage(message))
        scope.launch {
            snackbarHostState.showSnackbar(message.toMessage())
        }
    }

    state.navigationTarget?.let { target ->
        viewModel.onIntentTriggered(UserListIntent.ConsumeNavigation(target))
        when (target) {
            is UserListUiState.NavigationTarget.UserDetails -> {
                onUserDetailsClick(target.userId)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "GitHub Users") // TODO add resources
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        UserListScreenContent(
            contentState = state.contentState,
            onIntentTriggered = viewModel::onIntentTriggered,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

// TODO add resources
private fun UserListUiState.SnackBarMessage.toMessage(): String {
    return "Oops, something went wrong"
}