package com.safonov.feature.user.data.source.remote

import com.safonov.feature.user.data.source.remote.model.UserApiModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType

class UserApiService(private val client: HttpClient) {

    suspend fun getGitHubUsers(): List<UserApiModel> {
        val response: HttpResponse = client.get("https://api.github.com/users") {
            accept(ContentType.Application.Json)
        }
        return response.body()
    }
}

