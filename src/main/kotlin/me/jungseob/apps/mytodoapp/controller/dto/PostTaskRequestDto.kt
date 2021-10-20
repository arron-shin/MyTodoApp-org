package me.jungseob.apps.mytodoapp.controller.dto

import java.time.LocalDateTime

data class PostTaskRequestDto(
    val title: String,
    val memo: String,
    val deadLine: LocalDateTime,
    val checked: Boolean,
)
