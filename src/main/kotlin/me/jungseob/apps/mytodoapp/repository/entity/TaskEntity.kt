package me.jungseob.apps.mytodoapp.repository.entity

import java.time.Instant
import me.jungseob.apps.mytodoapp.service.model.Task
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("tasks")
data class TaskEntity(
    @Id
    val id: Long?,
    val title: String,
    val memo: String,
    val checked: Boolean,
    val deadline: Instant,
)

fun Task.toEntity() = TaskEntity(
    id = id,
    title = title,
    memo = memo,
    checked = checked,
    deadline = deadline,
)

fun TaskEntity.toModel() = Task(
    id = id,
    title = title,
    memo = memo,
    checked = checked,
    deadline = deadline,
)
