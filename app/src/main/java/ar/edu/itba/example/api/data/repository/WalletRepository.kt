package ar.edu.itba.example.api.data.repository

import ar.edu.itba.example.api.data.model.WalletDetail
import ar.edu.itba.example.api.data.network.WalletRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WalletRepository(
    private val remoteDataSource: WalletRemoteDataSource
) {
    val walletDetailStream: Flow<WalletDetail> =
        remoteDataSource.walletDetailStream
            .map { it.asModel() }
}