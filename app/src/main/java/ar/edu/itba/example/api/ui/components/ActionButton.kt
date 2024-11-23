package ar.edu.itba.example.api.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(
    @StringRes resId: Int,
    enabled: Boolean = true,
    onClick: () -> Unit,
    icon: @Composable (() -> Unit)? = null,
    displayText: Boolean? = false
) {
    Button(
        modifier = Modifier
            .padding(top = 16.dp, start = 12.dp, end = 12.dp),
        enabled = enabled,
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        content = {
            Column {
                //TODO: se podria mejorar el estilo de codigo.
                if (icon != null) {
                    icon()
                    if (displayText == true) {
                        Text(
                            text = stringResource(resId),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                } else {
                    Text(
                        text = stringResource(resId),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    )
    }
