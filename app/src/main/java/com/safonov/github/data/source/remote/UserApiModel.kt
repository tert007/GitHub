package com.safonov.github.data.source.remote

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val url: String
)