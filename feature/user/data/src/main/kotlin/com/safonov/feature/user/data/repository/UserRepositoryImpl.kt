package com.safonov.feature.user.data.repository

import com.safonov.core.data.source.local.dao.UserDao
import com.safonov.core.data.source.local.entity.UserEntity
import com.safonov.feature.user.data.repository.mapper.toEntity
import com.safonov.feature.user.data.repository.mapper.toModel
import com.safonov.feature.user.data.source.remote.UserApiService
import com.safonov.feature.user.data.source.remote.model.UserApiModel
import com.safonov.github.feature.user.domain.model.User
import com.safonov.github.feature.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class UserRepositoryImpl(
    private val userApiService: Lazy<UserApiService>,
    private val userDao: Lazy<UserDao>,
) : UserRepository {

    override suspend fun requestUsersUpdate(): Result<Unit> {
        return try {
            val usersFromNetwork = userApiService.value.getGitHubUsers().map(UserApiModel::toModel)
            userDao.value.insert(usersFromNetwork.map(User::toEntity))
            Result.success(Unit)
        } catch (e: Exception) {
            // TODO wrap exception with domain layer exception
            Result.failure(e)
        }
    }

    override fun getUsersStream(): Flow<List<User>> = userDao.value.getAllStream()
        .map { userEntities ->
            userEntities.map(UserEntity::toModel)
        }
}
