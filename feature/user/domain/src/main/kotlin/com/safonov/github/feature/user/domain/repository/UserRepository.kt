package com.safonov.github.feature.user.domain.repository

import com.safonov.github.feature.user.domain.model.User

interface UserRepository {
    suspend fun getUsers(): Result<List<User>>
}