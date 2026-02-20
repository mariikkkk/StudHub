package com.example.unigroup.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.unigroup.R

sealed class NavigationItem(val route: String, val icon: ImageVector? = null, val iconId: Int? = null, val title: String) {
    object Home    : NavigationItem("home_main", Icons.Default.Home, title = "Главная")
    object Queues  : NavigationItem("queues", Icons.Default.List, title = "Очереди")
    object Calendar: NavigationItem("calendar", Icons.Default.DateRange, title = "Календарь")
    object Requests: NavigationItem("requests", Icons.Default.MailOutline, title = "Запросы")
    object Files   : NavigationItem("files", iconId = R.drawable.file, title = "Файлы")

}