package ar.edu.itba.example.api.data.repository

import ar.edu.itba.example.api.data.network.UserRemoteDataSource

class UserRepository(
    private val remoteDataSource: UserRemoteDataSource
) {

    suspend fun login(username: String, password: String) {
        remoteDataSource.login(username, password)
    }

    suspend fun logout() {
        remoteDataSource.logout()
    }
}