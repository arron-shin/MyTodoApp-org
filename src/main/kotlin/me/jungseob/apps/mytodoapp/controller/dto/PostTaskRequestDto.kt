package me.jungseob.apps.mytodoapp.controller.dto

import java.time.LocalDateTime

data class PostTaskRequestDto(
    val title: String,
    val description: String,
    val deadLine: LocalDateTime,
)
