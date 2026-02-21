package com.example.unigroup.presentation.queues

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.unigroup.domain.models.QueueItem
import com.example.unigroup.domain.models.QueueSlot

class QueuesViewModel: ViewModel() {

    val queueList = listOf(
        QueueItem(
            1,
            "ТРПП 2",
            "Сегодня дедлайн!",
            26,
            5,
            "Идет сдача"
        ),
        QueueItem(
            2,
            "Геймдизайн 2",
            "Перед сдачей прикрепите",
            26,
            2,
            "Идет сдача"
        ),
        QueueItem(
            3,
            "ТРПП 1",
            "На первой паре можно",
            26,
            null,
            "Закрыто"
        ),
        QueueItem(
            4,
            "Первая беседа",
            "Файлы уже загружены",
            26,
            15,
            "Открыто"
        )
    )
    private val _slots =
        mutableStateListOf<QueueSlot>().apply { // внутренняя переменная, которую никто не может поменять
            repeat(26) { i ->
                add(QueueSlot(id = i + 1))
            }
        }
    val slots: List<QueueSlot> =
        _slots // Экран видит только эту переменную. Все изменения должны проходить через функции

    fun toggleSlot(slotId: Int, queueId: Int) {                            // вызывается при клике по ячейке
        val index = _slots.indexOfFirst { it.id == slotId }    // пробегается по списку и ищет id
        if (index != -1) {                                       // если нашли айди
            val currentSlot = _slots[index]                     // сохраняем состояние
            _slots[index] = when {
                // Если это наш слот, то освобождаем его
                currentSlot.isMySlot -> currentSlot.copy(studentName = null, isMySlot = false)
                // Если место пустое, то занимаем его
                currentSlot.studentName == null -> currentSlot.copy(
                    studentName = "Цой Марат",
                    isMySlot = true
                )
                // Если занято, то ничего не происходит
                else -> currentSlot
                // Используем copy, чтобы заменить указанные поля на новые и положить новый объект в массив _slots[index]
            }
        }
    }



    fun loadSlotsForQueue(queueId: Int) {
        val newSlots = when (queueId) {
            1 -> {
                (1..26).map {
                    QueueSlot(it, if (it == 5) "Куликов" else null, isMySlot = it == 5)

                }
            }
            2 -> {
                (1..26).map{
                    QueueSlot(it, if (it % 2 == 0) "Бурцев" else null, isMySlot = false)
                }
            }
            else -> {
                (1..26).map {
                    QueueSlot(it, null, isMySlot = false)
                }
            }
        }
        _slots.clear()
        _slots.addAll(newSlots)
        }
}