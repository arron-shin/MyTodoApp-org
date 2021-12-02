package me.jungseob.apps.mytodoapp.controller.dto

import java.time.LocalDateTime
import org.springframework.format.annotation.DateTimeFormat

data class PutTaskRequestDto(
    val title: String,
    val memo: String,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val deadline: LocalDateTime,
)
