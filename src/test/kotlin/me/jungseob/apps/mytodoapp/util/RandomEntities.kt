package me.jungseob.apps.mytodoapp.util

import java.time.Instant
import kotlin.random.Random
import me.jungseob.apps.mytodoapp.repository.entity.TaskEntity

fun randomTaskEntity(
    id: Long? = randomNonNegativeLong(),
    title: String = randomShortAlphanumeric(),
    memo: String = randomShortAlphanumeric(),
    checked: Boolean = Random.nextBoolean(),
    deadline: Instant = randomInstant(),
): TaskEntity = TaskEntity(
    id = id,
    title = title,
    memo = memo,
    checked = checked,
    deadline = deadline,
)
