package me.jungseob.apps.mytodoapp.controller.dto

import java.time.LocalDateTime

data class PostTaskResponseDto(
    val id: Long,
    val title: String,
    val memo: String,
    val checked: Boolean,
    val deadline: LocalDateTime,
)
