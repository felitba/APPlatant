package ar.edu.itba.example.api.data.model

import ar.edu.itba.example.api.data.network.model.NetworkPayment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Payment(
    var id: Int? = null,
    var amount: Double,
    var description: String,
    var type: PaymentType,
    var pending: Boolean,
    var createdAt: Date? = null,
    var updatedAt: Date? = null,
    var card: Card? = null,
    var linkUuid: String? = null,
    var receiverEmail: String? = null
) {

    fun validate()  {

        if(type == PaymentType.CARD && card == null)  {
            throw IllegalArgumentException("missing card id while generating payment")
        }
    }

    fun asNetworkModel(): NetworkPayment {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))
        return NetworkPayment(
            id = id,
            amount = amount,
            type = when (type) {
                PaymentType.BALANCE -> "BALANCE"
                PaymentType.CARD -> "CARD"
                else -> "LINK"
            },
            description = description,
            pending = pending,
            createdAt = createdAt?.let { dateFormat.format(it) },
            updatedAt = updatedAt?.let { dateFormat.format(it) },
            receiverEmail = receiverEmail,
            card = card,
            linkUuid = linkUuid,
        )
    }
}
