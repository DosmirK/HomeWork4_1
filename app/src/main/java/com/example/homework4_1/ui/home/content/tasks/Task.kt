package com.example.homework4_1.ui.home.content.tasks

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    var text: String = String(),
    var position: Int = 0,
    var type: TypeOfTask = TypeOfTask.URGENT_TASKS
) : Parcelable
@Parcelize
enum class TypeOfTask(
    val id: Int
): Parcelable {
    ALL_TASKS(0),
    URGENT_TASKS(1),
    LONG_TASKS(2),
    COMPLETED(3)
}
