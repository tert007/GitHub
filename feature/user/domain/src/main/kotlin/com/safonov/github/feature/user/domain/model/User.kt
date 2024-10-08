package com.safonov.github.feature.user.domain.model

data class User(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val url: String
)