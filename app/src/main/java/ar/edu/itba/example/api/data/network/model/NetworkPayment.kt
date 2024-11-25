package ar.edu.itba.example.api.data.network.model

import ar.edu.itba.example.api.data.model.Payment
import ar.edu.itba.example.api.data.model.PaymentType
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
class NetworkPayment(
    var id: Int?=null,
    var type: String,
    var amount: Float,

    var balanceBefore:  Float?=null,
    var balanceAfter:  Float?=null,
    var receiverBalanceBefore: Float?=null,
    var receiverBalanceAfter:  Float?=null,

    var pending: Boolean?=null,
    var linkUuid: String?=null,
    var createdAt: String?=null,
    var updatedAt: String?=null,
    var card: NetworkCard?=null,
    var payer : NetworkUser?=null,
    var receiver : NetworkUser?=null,

    var description: String? = null,
    var cardId: Int? = null,
    var receiverEmail: String? = null,



    ) {
    fun asModel(): Payment {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))
        return Payment(
            id = id,
            type = when (type) {
                "BALANCE" -> PaymentType.BALANCE
                "CARD" -> PaymentType.CARD
                else -> PaymentType.LINK
            },
            amount = amount,

            balanceBefore =  balanceBefore,
            balanceAfter = balanceAfter,
            receiverBefore = receiverBalanceBefore,
            receiverAfter = receiverBalanceAfter,

            pending = pending,
            linkUuid = linkUuid,
            createdAt = createdAt?.let { dateFormat.parse(createdAt!!) },
            updatedAt = updatedAt?.let { dateFormat.parse(updatedAt!!) },


            card = card?.asModel(),
            payer = payer?.asModel(),
            receiver = receiver?.asModel(),

            receiverEmail = receiverEmail,
            description = description,
            cardId = cardId,

        )
    }
}
