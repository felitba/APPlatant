package ar.edu.itba.example.api.data.model

import ar.edu.itba.example.api.data.network.model.NetworkPayment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Payment(
    var id: Int?=null,
    var type: PaymentType,
    var amount: Float,

    var balanceBefore: Float?=null,
    var balanceAfter: Float?=null,
    var receiverBefore:Float?=null,
    var receiverAfter: Float?=null,
    var pending: Boolean?=null,
    var linkUuid: String?=null,
    var createdAt: Date?=null,
    var updatedAt: Date?=null,
    var card: Card?=null,
    var payer : User?=null,
    var receiver : User?=null,

    var description: String? =null,
    var cardId: Int? = null,
    var receiverEmail: String? = null,


) {

    fun asNetworkModel(): NetworkPayment {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))
        return NetworkPayment(
            id = id,
            type = when (type) {
                PaymentType.BALANCE -> "BALANCE"
                PaymentType.CARD -> "CARD"
                else -> "LINK"
            },
            amount = amount,

            balanceBefore = balanceBefore,
            balanceAfter = balanceAfter,
            receiverBalanceBefore = receiverBefore,
            receiverBalanceAfter = receiverAfter,

            pending = pending,
            linkUuid = linkUuid,
            createdAt = createdAt?.toString(),
            updatedAt = updatedAt?.toString(),
            card = card?.asNetworkModel(),
            payer = payer?.asNetworkModel(),
            receiver = receiver?.asNetworkModel(),

            receiverEmail = receiverEmail,
            cardId = cardId,
            description = description,

        )
    }
}
