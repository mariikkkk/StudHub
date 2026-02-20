package com.example.unigroup.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush

object AppBrushes{
    val titleGradient: Brush
    @Composable
    get() = Brush.linearGradient(
        colors = listOf(
            titleGradient1,
            titleGradient2
        )
    )
}