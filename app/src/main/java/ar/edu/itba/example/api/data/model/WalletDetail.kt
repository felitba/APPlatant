package ar.edu.itba.example.api.data.model

import java.util.Date

class WalletDetail(
    var id: Int,
    var balance: Double,
    var invested: Double,
    var cbu: String,
    var alias: String,
    var createdAt: Date,
    var updatedAt: Date
) {
}