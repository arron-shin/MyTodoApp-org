package me.jungseob.apps.mytodoapp.repository.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import me.jungseob.apps.mytodoapp.service.model.Task

@Entity
@Table(name = "tasks")
data class TaskEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    val title: String,
    val memo: String,
    val checked: Boolean,
    val deadline: LocalDateTime,
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
