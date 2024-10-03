package com.safonov.feature.user.data.repository

import com.safonov.feature.user.data.repository.mapper.toModel
import com.safonov.feature.user.data.source.remote.UserApiService
import com.safonov.feature.user.data.source.remote.model.UserApiModel
import com.safonov.github.feature.user.domain.model.User
import com.safonov.github.feature.user.domain.repository.UserRepository

internal class UserRepositoryImpl(
    private val userApiService: Lazy<UserApiService>
) : UserRepository {
    override suspend fun getUsers(): Result<List<User>> {
        val users = userApiService.value.getGitHubUsers().map(UserApiModel::toModel)
        return Result.success(users)
    }
}