package me.jungseob.apps.mytodoapp.controller

import me.jungseob.apps.mytodoapp.controller.dto.PostTaskRequestDto
import me.jungseob.apps.mytodoapp.controller.dto.PutTaskRequestDto
import me.jungseob.apps.mytodoapp.service.TaskService
import me.jungseob.apps.mytodoapp.service.model.Task
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/tasks")
class TaskApiController(
    private val taskService: TaskService
) {

    @GetMapping("/{id}")
    fun getTask(@PathVariable("id") id: Long): Task? {
        return taskService.getTask(id)
    }

    @GetMapping("/{id}/test")
    fun getTest(@PathVariable("id") id: String): String {
        return id
    }

    @PostMapping
    fun postTask(@RequestBody postTaskRequestDto: PostTaskRequestDto): Task {
        return taskService.createTask(
            title = postTaskRequestDto.title,
            memo = postTaskRequestDto.memo,
            checked = postTaskRequestDto.checked,
            deadline = postTaskRequestDto.deadline,
        )
    }

    @PutMapping("/{id}")
    fun modifyTask(
        @PathVariable("id") id: Long,
        @RequestBody putTaskRequestDto: PutTaskRequestDto,
    ): Task {
        return taskService.updateTask(
            id = id,
            title = putTaskRequestDto.title,
            memo = putTaskRequestDto.memo,
            deadline = putTaskRequestDto.deadline,
        )
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable("id") id: Long) {
        taskService.deleteTask(id)
    }

    @GetMapping
    fun list(): List<Task> {
        return taskService.list()
    }
}
