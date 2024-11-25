package ar.edu.itba.example.api.data.network.model

import ar.edu.itba.example.api.data.model.RegisterUser
import ar.edu.itba.example.api.data.model.User
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
class NetworkRegisterUser(

    var firstName: String,
    var lastName: String,
    var email: String,
    var birthDate: String,
    var password: String
) {
    fun asModel(): RegisterUser {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))

        return RegisterUser(
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password,
            birthDate = dateFormat.parse(birthDate)!!
        )
    }
}