package com.safonov.feature.user.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.safonov.feature.user.presentation.list.mvi.UserListIntent
import com.safonov.feature.user.presentation.list.mvi.UserListUiState
import com.safonov.github.feature.user.domain.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UserListScreenContent(
    contentState: UserListUiState.ContentState,
    modifier: Modifier = Modifier,
    onIntentTriggered: (UserListIntent) -> Unit
) {
    // TODO set up animation
    when (contentState) {
        UserListUiState.ContentState.Loading -> Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        is UserListUiState.ContentState.Loaded -> {
            val pullToRefreshState = rememberPullToRefreshState()
            PullToRefreshBox(
                modifier = modifier,
                state = pullToRefreshState,
                isRefreshing = contentState.isRefreshing,
                onRefresh = {
                    onIntentTriggered(UserListIntent.Refresh)
                },
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(items = contentState.items, key = User::id) { user ->
                        UserItem(
                            user = user,
                            onClick = {
                                onIntentTriggered(UserListIntent.UserClick(user.id))
                            },
                            modifier = itemModifier
                        )
                    }
                }
            }
        }
    }
}

private val itemModifier = Modifier
    .fillMaxWidth()
    .padding(horizontal = 16.dp, vertical = 8.dp)
private val AvatarSize = 40.dp

@Composable
private fun UserItem(
    user: User,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                .size(AvatarSize)
        )
        Text(text = user.login)
    }
}
