package ar.edu.itba.example.api.data.network.model

import ar.edu.itba.example.api.data.model.WalletDetail
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
class NetworkWalletDetail(
    var id: Int,
    var balance: Double,
    var invested: Double,
    var cbu: String,
    var alias: String,
    var createdAt: String,
    var updatedAt: String
) {
    fun asModel(): WalletDetail {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))
        return WalletDetail(
            id = id,
            balance = balance,
            invested = invested,
            cbu = cbu,
            alias = alias,
            createdAt = dateFormat.parse(createdAt)!!,
            updatedAt = dateFormat.parse(updatedAt)!!
        )
    }
}
