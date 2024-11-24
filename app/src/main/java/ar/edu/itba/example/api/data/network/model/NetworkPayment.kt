package ar.edu.itba.example.api.data.network.model

import ar.edu.itba.example.api.data.model.Payment
import ar.edu.itba.example.api.data.model.PaymentType
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
class NetworkPayment(
    var id: Int? = null,
    var amount: Double,
    var type: String,
    var linkUuid: String? = null,
    var pending: Boolean?=null,
    var createdAt: String?,
    var updatedAt: String?,
    var card: NetworkCard? = null,
    var cardId: Int? = null,
    var description: String,
    var receiverEmail: String? = null,
    var balanceBefore: Double? = null,
    var balanceAfter: Double? = null

    ) {
    fun asModel(): Payment {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))
        return Payment(
            id = id,
            amount = amount,
            type = when (type) {
                "BALANCE" -> PaymentType.BALANCE
                "CARD" -> PaymentType.CARD
                else -> PaymentType.LINK
            },
            pending = pending,
            createdAt = createdAt?.let { dateFormat.parse(it) },
            updatedAt = updatedAt?.let { dateFormat.parse(it) },
            receiverEmail = receiverEmail,
            card = card?.asModel(),
            description = description,
            linkUuid = linkUuid,
            balanceBefore =  balanceBefore,
            balanceAfter = balanceAfter,
            cardId = cardId
        )
    }
}
