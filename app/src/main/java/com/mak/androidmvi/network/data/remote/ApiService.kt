package com.mak.androidmvi.network.data.remote

/*
class ApiService(private val client: HttpClient, private val baseUrl: String) {

    suspend fun getUser(id: String): UserDto {
        return client.get("$baseUrl/users/$id").body()
    }

    suspend fun login(email: String, password: String): TokenResponse {
        return client.post("$baseUrl/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(email, password))
        }.body()
    }
}*/
