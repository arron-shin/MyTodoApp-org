package me.jungseob.apps.mytodoapp.service

import java.time.Instant
import me.jungseob.apps.mytodoapp.exception.MyNotFoundException
import me.jungseob.apps.mytodoapp.repository.TaskR2dbcRepository
import me.jungseob.apps.mytodoapp.repository.entity.toEntity
import me.jungseob.apps.mytodoapp.repository.entity.toModel
import me.jungseob.apps.mytodoapp.service.model.Task
import org.springframework.stereotype.Service

@Service
class TaskService(
    private val taskR2dbcRepository: TaskR2dbcRepository,
) {

    suspend fun createTask(
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
        val savedTaskEntity = taskR2dbcRepository.insert(task.toEntity())
        return savedTaskEntity.toModel()
    }

    suspend fun getTask(id: Long): Task? {
        val taskEntity = taskR2dbcRepository.findById(id)
            ?: throw MyNotFoundException("Not found resource.")
        return taskEntity.toModel()
    }

    suspend fun list(): List<Task> {
        val taskEntities = taskR2dbcRepository.findAll()
        return taskEntities.map { it.toModel() }
    }

    suspend fun updateTask(
        task: Task
    ): Task {
        taskR2dbcRepository.update(task.toEntity())
        return task
    }

    suspend fun deleteTask(id: Long) {
        taskR2dbcRepository.deleteById(id)
    }
}
