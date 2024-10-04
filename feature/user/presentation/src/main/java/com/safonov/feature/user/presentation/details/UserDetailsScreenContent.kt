package com.safonov.feature.user.presentation.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.safonov.feature.user.presentation.details.mvi.UserDetailsIntent
import com.safonov.feature.user.presentation.details.mvi.UserDetailsUiState
import com.safonov.github.feature.user.domain.model.User

@Composable
internal fun UserDetailsScreenContent(
    contentState: UserDetailsUiState.ContentState,
    onIntentTriggered: (UserDetailsIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        when (contentState) {
            UserDetailsUiState.ContentState.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            is UserDetailsUiState.ContentState.Loaded -> {
                if (contentState.user == null) {
                    UserFailedToLoad(modifier = Modifier.fillMaxSize())
                } else {
                    UserDetails(
                        user = contentState.user,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun UserFailedToLoad(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Failed to load user")
    }
}

private val ImageSize = 128.dp

@Composable
private fun UserDetails(user: User, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.avatarUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(ImageSize)
        )
        Spacer(Modifier.height(12.dp))
        Text(text = user.login)
    }
}