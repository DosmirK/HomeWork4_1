package com.example.homework4_1.ui.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    val img: String,
    val name: String
): Parcelable
