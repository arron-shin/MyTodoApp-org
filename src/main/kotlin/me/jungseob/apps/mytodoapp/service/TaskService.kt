package me.jungseob.apps.mytodoapp.service

import java.time.Instant
import java.time.LocalDateTime
import me.jungseob.apps.mytodoapp.exception.MyNotFoundException
import me.jungseob.apps.mytodoapp.repository.TaskRepository
import me.jungseob.apps.mytodoapp.repository.entity.toEntity
import me.jungseob.apps.mytodoapp.repository.entity.toModel
import me.jungseob.apps.mytodoapp.service.model.Task
import org.springframework.stereotype.Service

@Service
class TaskService(
    private val taskRepository: TaskRepository
) {

    fun createTask(
        title: String,
        memo: String,
        checked: Boolean,
        deadline: Instant,
    ): Task {
        val task = Task(
            title = title,
            memo = memo,
            checked = checked,
            deadline = deadline,
        )
        val savedTaskEntity = taskRepository.save(task.toEntity())
        return savedTaskEntity.toModel()
    }

    fun getTask(id: Long): Task? {
        val optionalTaskEntity = taskRepository.findById(id)
        return optionalTaskEntity
            .orElseThrow {
                throw MyNotFoundException("Not found resource.")
            }.toModel()
    }

    fun list(): List<Task> {
        val taskEntities = taskRepository.findAll()
        return taskEntities.map { it.toModel() }
    }

    fun updateTask(
        id: Long,
        title: String,
        memo: String,
        deadline: Instant,
    ): Task {
        val optionalTaskEntity = taskRepository.findById(id)
        return optionalTaskEntity.map { taskEntity ->
            val updatedEntity = taskEntity.copy(
                title = title,
                memo = memo,
                deadline = deadline,
            )
            taskRepository.save(updatedEntity).toModel()
        }.orElseThrow {
            throw MyNotFoundException("Not found resource.")
        }
    }

    fun deleteTask(id: Long) {
        taskRepository.deleteById(id)
    }
}
