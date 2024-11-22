package ar.edu.itba.example.api.data.network.model
import ar.edu.itba.example.api.data.model.Amount
import kotlinx.serialization.Serializable


@Serializable
class NetworkAmount(
    var amount: Double,
) {
    fun asModel(): Amount {
        return Amount(
           amount = amount,
        )
    }
}