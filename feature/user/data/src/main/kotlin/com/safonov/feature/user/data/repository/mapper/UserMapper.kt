package com.safonov.feature.user.data.repository.mapper

import com.safonov.core.data.source.local.entity.UserEntity
import com.safonov.feature.user.data.source.remote.model.UserApiModel
import com.safonov.github.feature.user.domain.model.User

internal fun UserApiModel.toModel() = User(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    url = url
)

internal fun UserEntity.toModel() = User(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    url = url
)

internal fun UserApiModel.toEntity() = UserEntity(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    url = url
)