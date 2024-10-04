package com.safonov.github

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object UserList : Screen()

    @Serializable
    data class UserDetails(val userId: Int) : Screen()
}