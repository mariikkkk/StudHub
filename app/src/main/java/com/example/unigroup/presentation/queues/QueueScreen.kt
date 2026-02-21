package com.example.unigroup.presentation.queues

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unigroup.R
import com.example.unigroup.domain.models.QueueItem
import com.example.unigroup.domain.models.QueueSlot
import com.example.unigroup.presentation.theme.UniGroupTheme

@Composable
fun QueuesTab(viewModel: QueuesViewModel = viewModel()){
    var selectedQueueId by remember { mutableStateOf<Int?>(null) } // Хранит id выбранной очереди

    if (selectedQueueId == null){
        // 1 уровень со списком очередей
        QueuesListScreen(
            queues = viewModel.queueList,
            onQueueClick = { queueId ->
                viewModel.loadSlotsForQueue(queueId)
                selectedQueueId = queueId // Пользователь кликнул => меняем состояние
            }

        )
    }else{
        // Второй уровень с деталями очереди
        QueueDetailsScreen(
            viewModel = viewModel,
            onBackClick = { selectedQueueId = null },
            selectedQueueId!!
        )
    }

}

@Composable
fun QueueSlotItem(slot: QueueSlot, onClick: () -> Unit){
    val backgroundColor = when{
        slot.isMySlot -> MaterialTheme.colorScheme.primaryContainer
        slot.studentName != null -> MaterialTheme.colorScheme.surfaceVariant
        else -> MaterialTheme.colorScheme.surface
    }
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "${slot.id}.", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = slot.studentName ?: "Свободно",
                style = MaterialTheme.typography.bodyLarge,
                color = if (slot.studentName == null) Color.Gray else Color.Unspecified
            )


        }
    }
}

@Composable
fun QueuesListScreen(queues: List<QueueItem>, onQueueClick: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
            Icon(
                Icons.Default.List,
                contentDescription = "Очереди",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Очереди",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                )
            {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Создать новую очередь",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(2.dp))
                Text("Создать")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(queues) { queue ->
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { onQueueClick(queue.id) },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)

                ) {
                    Column() {
                        Column() {
                            Text(
                                queue.title,
                                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                queue.subtitle,
                                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                            )

                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                        Row(){
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically){
                                Icon(painter = painterResource(R.drawable.persons), contentDescription = "Иконка очереди")
                                Spacer(modifier = Modifier.padding(2.dp))
                                Text("${queue.participantsCount} чел.")
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                "Ваше место: ${queue.myPlace ?: "не записан"} ",
                                modifier = Modifier.padding(12.dp),
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }

                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun QueueDetailsScreen(viewModel: QueuesViewModel, onBackClick: () -> Unit, queueId: Int) {
    val selectedQueue = viewModel.queueList.find { it.id == queueId }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, contentDescription = "Назад") }
        }
        Text(
            text = selectedQueue?.title ?: "Ошибка",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(viewModel.slots) { slot ->
                QueueSlotItem(slot = slot, onClick = { viewModel.toggleSlot(slot.id, queueId) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UniGroupTheme {
        QueuesListScreen(
            listOf(
                QueueItem(1, "Queue 1", "Subtitle 1", 10, null, "Status 1"),
                QueueItem(1, "Queue 1", "Subtitle 1", 10, 5, "Status 1")
            ), onQueueClick = {})
    }
}