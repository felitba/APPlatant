package ar.edu.itba.example.api.data.network

import ar.edu.itba.example.api.data.network.api.UserApiService
import ar.edu.itba.example.api.data.network.model.NetworkCredentials
import ar.edu.itba.example.api.SessionManager

class UserRemoteDataSource(
    private val sessionManager: SessionManager,
    private val userApiService: UserApiService
) : RemoteDataSource() {

    suspend fun login(username: String, password: String) {
        val response = handleApiResponse {
            userApiService.login(NetworkCredentials(username, password))
        }
        sessionManager.saveAuthToken(response.token)
    }

    suspend fun logout() {
        handleApiResponse { userApiService.logout() }
        sessionManager.removeAuthToken()
    }
}