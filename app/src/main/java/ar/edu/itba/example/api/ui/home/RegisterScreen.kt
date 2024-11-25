package ar.edu.itba.example.api.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import ar.edu.itba.example.api.R
import ar.edu.itba.example.api.ui.components.ActionButton
import ar.edu.itba.example.api.ui.components.DatePickerModal

@Composable
fun RegisterView(
    viewModel: HomeViewModel,
    onCancelNavigate: () -> Unit,
    onSubmitNavigate: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var birthDate by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    var wrongAttempt by remember { mutableStateOf(false) }
    var verifying by rememberSaveable { mutableStateOf(false) }

    var errors by remember { mutableStateOf(mapOf<String, Boolean>()) }

    var isDatePickerVisible by remember { mutableStateOf(false) }

    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var isConfirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

    val dateFormatter = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.medium_padding)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding))
    ) {
        OutlinedTextField(
            value = firstName,
            onValueChange = {
                firstName = it
                errors = errors - "firstName"
            },
            label = { Text(stringResource(R.string.first_name)) },
            singleLine = true,
            isError = errors["firstName"] == true,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (firstName.isNotEmpty()) {
                    IconButton(onClick = { firstName = "" }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            }
        )
        if (errors["firstName"] == true) {
            RequiredErrorMessage()
        }

        OutlinedTextField(
            value = lastName,
            onValueChange = {
                lastName = it
                errors = errors - "lastName"
            },
            label = { Text(stringResource(R.string.last_name)) },
            singleLine = true,
            isError = errors["lastName"] == true,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (lastName.isNotEmpty()) {
                    IconButton(onClick = { lastName = "" }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            }
        )
        if (errors["lastName"] == true) {
            RequiredErrorMessage()
        }

        OutlinedTextField(
            value = birthDate,
            onValueChange = {},
            label = { Text(stringResource(R.string.birth_date)) },
            readOnly = true,
            singleLine = true,
            isError = errors["birthDate"] == true,
            modifier = Modifier
                .fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { isDatePickerVisible = true }) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null
                       )
                }
            }
        )
        if (errors["birthDate"] == true) {
            RequiredErrorMessage()
        }

        if (isDatePickerVisible) {
            DatePickerModal (
                onDismiss = { isDatePickerVisible = false },
                onDateSelected = { selectedDate ->
                    birthDate = selectedDate.let { dateFormatter.format(it?.let { it1 ->
                        java.util.Date(
                            it1
                        )
                    }!!) }
                    errors = errors - "birthDate"
                    isDatePickerVisible = false
                }
            )
        }

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errors = errors - "email"
            },
            label = { Text(stringResource(R.string.Email)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            isError = errors["email"] == true,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (email.isNotEmpty()) {
                    IconButton(onClick = { email = "" }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            }
        )
        if (errors["email"] == true) {
            RequiredErrorMessage()
        }

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errors = errors - "password"
            },
            label = { Text(stringResource(R.string.password)) },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            isError = errors["password"] == true,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = null
                )
            },
            trailingIcon = {
                val image = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                val description = if (isPasswordVisible) stringResource(R.string.hide_password) else stringResource(R.string.show_password)

                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            }
        )
        if (errors["password"] == true) {
            RequiredErrorMessage()
        }

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                errors = errors - "confirmPassword"
            },
            label = { Text(stringResource(R.string.confirm_password)) },
            visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            isError = errors["confirmPassword"] == true || password != confirmPassword,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = null
                )
            },
            trailingIcon = {
                val image = if (isConfirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                val description = if (isConfirmPasswordVisible) stringResource(R.string.hide_password) else stringResource(R.string.show_password)

                IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            }
        )
        if (errors["confirmPassword"] == true) {
            RequiredErrorMessage()
        } else if (password != confirmPassword) {
            Text(
                text = stringResource(R.string.no_match_password),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ActionButton(
                resId = R.string.cancel,
                onClick = {
                    firstName = ""
                    lastName = ""
                    birthDate= ""
                    email = ""
                    password = ""
                    confirmPassword = ""
                    onCancelNavigate()
                },
            )

            ActionButton(
                resId = R.string.confirm,
                onClick = {
                    val newErrors = mutableMapOf<String, Boolean>()

                    if (firstName.isEmpty()) newErrors["firstName"] = true
                    if (lastName.isEmpty()) newErrors["lastName"] = true
                    if (birthDate.isEmpty()) newErrors["birthDate"] = true
                    if (email.isEmpty()) newErrors["email"] = true
                    if (password.isEmpty()) newErrors["password"] = true
                    if (confirmPassword.isEmpty() || password != confirmPassword) newErrors["confirmPassword"] = true

                    if (newErrors.isEmpty()) {
                        viewModel.registerUser(firstName = firstName, lastName = lastName,
                            birthDate = birthDate, email = email,  password =password)
                        if (uiState.error != null) {
                            //verifying = true
                            onSubmitNavigate()
                        }
                    } else {
                        errors = newErrors
                    }
                },
            )
        }

    }

    if (wrongAttempt) {
        AlertDialog(
            onDismissRequest = {

            },
            title = { Text(stringResource(R.string.failed_register)) },
            text = { Text(stringResource(R.string.failed_register_dialog)) },
            confirmButton = {
                Button(
                    onClick = {
                        wrongAttempt = false
                        firstName = ""
                        lastName = ""
                        birthDate= ""
                        email = ""
                        password = ""
                        confirmPassword = ""
                    }
                ) {
                    Text(stringResource(R.string.accept))
                }
            }
        )
    }

    /*if (verifying) {
        var code by rememberSaveable { mutableStateOf("") }
        var errorCode by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = {

            },
            title = { Text(stringResource(R.string.verify_mail)) },
            text = {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.medium_padding)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding))
                ) {
                    Text(stringResource(R.string.verify_mail_dialog)) }
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.small_padding)))
                OutlinedTextField(
                    value = code,
                    onValueChange = {
                        code = it
                    },
                    label = { Text(stringResource(R.string.code)) },
                    singleLine = true,
                    isError = errors["firstName"] == true,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        if (code.isNotEmpty()) {
                            IconButton(onClick = { code = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                )
                if (errorCode) {
                    Text(
                        text = stringResource(R.string.incorrect_code),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },



            confirmButton = {
                Button(
                    onClick = {
                        viewModel.verify(code)
                        if (uiState.error != null) {
                            verifying = false
                            errorCode = false
                            onSubmitNavigate()
                        } else {
                            errorCode = true
                            uiState.error
                        }

                    }
                ) {
                    Text(stringResource(R.string.accept))
                }
            }
        )
    }*/
}



@Composable
fun RequiredErrorMessage() {
    Text(
        text = stringResource(R.string.mandatory_field_error),
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.labelSmall
    )
}