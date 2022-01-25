package me.jungseob.apps.mytodoapp.controller.dto

import java.time.Instant
import org.springframework.format.annotation.DateTimeFormat

data class PutTaskRequestDto(
    val title: String,
    val memo: String,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val deadline: Instant,
)
