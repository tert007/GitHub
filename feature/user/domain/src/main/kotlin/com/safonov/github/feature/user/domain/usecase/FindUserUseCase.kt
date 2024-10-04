package com.safonov.github.feature.user.domain.usecase

import com.safonov.github.feature.user.domain.repository.UserRepository

class FindUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: Int) = repository.findUser(id)
}