package com.safonov.feature.user.presentation.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.safonov.feature.user.presentation.details.mvi.UserDetailsUiState
import com.safonov.feature.user.presentation.details.mvi.UserDetailsViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(
    userId: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: UserDetailsViewModel = koinViewModel { parametersOf(userId) }
    val state: UserDetailsUiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.getUserLoginOrNull() ?: "Loading...")
                }
            )
        }
    ) { paddingValues ->
        UserDetailsScreenContent(
            contentState = state.contentState,
            onIntentTriggered = viewModel::onIntentTriggered,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

private fun UserDetailsUiState.getUserLoginOrNull(): String? {
    return (contentState as? UserDetailsUiState.ContentState.Loaded)?.user?.login
}