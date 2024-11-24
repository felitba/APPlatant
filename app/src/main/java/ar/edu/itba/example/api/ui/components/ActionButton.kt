package ar.edu.itba.example.api.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
    icon: @Composable (() -> Unit)? = null
) {
    Button(
        modifier = Modifier
            .padding(top = dimensionResource(R.dimen.medium_padding), start = dimensionResource(R.dimen.medium_padding), end = dimensionResource(R.dimen.medium_padding)),
        enabled = enabled,
        onClick = onClick,
        content = {
            if (icon != null) {
                icon()
            }
            else {
                Text(
                    text = stringResource(resId),
                    modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
                )
            }
        }
    )
    }
