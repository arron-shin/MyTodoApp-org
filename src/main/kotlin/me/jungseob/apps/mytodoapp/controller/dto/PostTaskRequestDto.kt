package me.jungseob.apps.mytodoapp.controller.dto

import java.time.Instant

data class PostTaskRequestDto(
    val title: String,
    val memo: String,
    val checked: Boolean = false,
    val deadline: Instant,
)
