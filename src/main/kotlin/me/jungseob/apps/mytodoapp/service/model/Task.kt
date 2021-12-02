package me.jungseob.apps.mytodoapp.service.model

import java.time.LocalDateTime

data class Task(
    val id: Long? = null,
    val title: String,
    val memo: String,
    val checked: Boolean,
    val deadline: LocalDateTime,
)
