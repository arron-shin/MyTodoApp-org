package me.jungseob.apps.mytodoapp.controller

import me.jungseob.apps.mytodoapp.controller.dto.PostTaskRequestDto
import me.jungseob.apps.mytodoapp.controller.dto.PutTaskRequestDto
import me.jungseob.apps.mytodoapp.service.TaskService
import me.jungseob.apps.mytodoapp.service.model.Task
import mu.KotlinLogging
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/v1/tasks")
class TaskApiController(
    private val taskService: TaskService
) {

    @GetMapping("/{id}")
    suspend fun getTask(@PathVariable("id") id: Long): Task? {
        logger.info { "[API] GET /api/v1/tasks/$id" }
        return taskService.getTask(id)
    }

    @PostMapping
    suspend fun postTask(@RequestBody postTaskRequestDto: PostTaskRequestDto): Task {
        logger.info { "[API] POST /api/v1/tasks (requestBody: $postTaskRequestDto)" }
        return taskService.createTask(
            title = postTaskRequestDto.title,
            memo = postTaskRequestDto.memo,
            checked = postTaskRequestDto.checked,
            deadline = postTaskRequestDto.deadline,
        )
    }

    @PutMapping("/{id}")
    suspend fun modifyTask(
        @PathVariable("id") id: Long,
        @RequestBody putTaskRequestDto: PutTaskRequestDto,
    ): Task {
        logger.info { "[API] PUT /api/v1/tasks/$id (requestBody: $putTaskRequestDto)" }
        val task = Task(
            id = id,
            title = putTaskRequestDto.title,
            memo = putTaskRequestDto.memo,
            checked = putTaskRequestDto.checked,
            deadline = putTaskRequestDto.deadline,
        )
        return taskService.updateTask(task)
    }

    @DeleteMapping("/{id}")
    suspend fun deleteTask(@PathVariable("id") id: Long) {
        logger.info { "[API] DELETE /api/v1/tasks/$id" }
        taskService.deleteTask(id)
    }

    @GetMapping
    suspend fun listTask(): List<Task> {
        logger.info { "[API] GET /api/v1/tasks" }
        return taskService.list()
    }
}
