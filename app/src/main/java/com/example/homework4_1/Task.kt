package com.example.homework4_1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    var text: String,
    var finished: Boolean = false,
    var position: Int = 0
) : Parcelable
