package ar.edu.itba.example.compose

import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "phone",
    group = "screen sizes",
    device = "spec:width=411dp,height=891dp"
)

@Preview(
    name = "tablet",
    group = "screen sizes",
    device = "spec:width=1280dp,height=800dp,dpi=240"
)

annotation class PreviewScreenSizes