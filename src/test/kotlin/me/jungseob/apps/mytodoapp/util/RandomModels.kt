package me.jungseob.apps.mytodoapp.util

import java.time.LocalDateTime
import kotlin.random.Random
import me.jungseob.apps.mytodoapp.service.model.Task

fun randomTask(
    id: Long = randomNonNegativeLong(),
    title: String = randomShortAlphanumeric(),
    memo: String = randomShortAlphanumeric(),
    checked: Boolean = Random.nextBoolean(),
    deadline: LocalDateTime = LocalDateTime.now(),
): Task = Task(
    id = id,
    title = title,
    memo = memo,
    checked = checked,
    deadline = deadline,
)
