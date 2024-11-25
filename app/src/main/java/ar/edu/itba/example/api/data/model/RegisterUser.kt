package ar.edu.itba.example.api.data.model

import java.util.Date

data class RegisterUser(
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var birthDate: Date
)

