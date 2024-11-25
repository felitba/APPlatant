package ar.edu.itba.example.api.data.network

import ar.edu.itba.example.api.data.network.api.UserApiService
import ar.edu.itba.example.api.data.network.model.NetworkCredentials
import ar.edu.itba.example.api.SessionManager
import ar.edu.itba.example.api.data.network.model.NetworkRegisterUser
import ar.edu.itba.example.api.data.network.model.NetworkUser

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

    suspend fun getCurrentUser(): NetworkUser {
        return handleApiResponse { userApiService.getCurrentUser() }
    }

    suspend fun registerUser(firstName: String, lastName: String, birthDate: String, email: String, password: String): NetworkUser {
        val response = handleApiResponse {
            userApiService.registerUser(NetworkRegisterUser(firstName, lastName, email, birthDate, password))
        }
        return response
    }

    suspend fun verify(code: String): NetworkUser {
        return handleApiResponse { userApiService.verify(code) }
    }
}