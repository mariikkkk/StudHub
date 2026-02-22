package com.example.studhub.presentation.queues

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import com.example.studhub.domain.models.QueueItem
import com.example.studhub.presentation.theme.statusGreenSurface
import com.example.studhub.presentation.theme.statusGreenText
import com.example.studhub.presentation.theme.statusRedSurface
import com.example.studhub.presentation.theme.statusRedText

fun QueueItem.getStatusTheme(): Pair<Color, Color>{
    return when (this.status){
        "Открыто" -> statusGreenSurface to statusGreenText
        "Закрыто" -> statusRedSurface to statusRedText
        else -> Color.Gray to Color.DarkGray

    }
}