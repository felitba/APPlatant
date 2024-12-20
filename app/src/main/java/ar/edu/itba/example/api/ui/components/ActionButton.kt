package ar.edu.itba.example.api.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ar.edu.itba.example.api.R

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
            .padding(top = dimensionResource(R.dimen.medium_padding), start = dimensionResource(R.dimen.intermediate_padding), end = dimensionResource(R.dimen.intermediate_padding)),
        enabled = enabled,
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        content = {
            Column {
                if (icon != null) {
                    icon()
                    if (displayText == true) {
                        Text(
                            text = stringResource(resId),
                            modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
                        )
                    }
                } else {
                    Text(
                        text = stringResource(resId),
                        modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
                    )
                }
            }
        }
    )
    }
