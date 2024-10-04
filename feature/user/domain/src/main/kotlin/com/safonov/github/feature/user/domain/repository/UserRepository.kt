package com.safonov.github.feature.user.domain.repository

import com.safonov.github.feature.user.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun findUser(id: Int): User?
    fun getUsersStream(): Flow<List<User>>

    /**
     * @return [Result] with [Unit] if users were updated successfully, otherwise [Result] with [Exception]
     */
    suspend fun requestUsersUpdate(): Result<Unit>
}
