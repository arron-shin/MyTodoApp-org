package me.jungseob.apps.mytodoapp.controller

import me.jungseob.apps.mytodoapp.controller.dto.PostTaskRequestDto
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/tasks")
class TaskApiController {

    @GetMapping("/{id}")
    fun getTask(@PathVariable("id") id: Long) {
        TODO()
    }

    @PostMapping
    fun postTask(@RequestBody postTaskRequestDto: PostTaskRequestDto) {
        TODO()
    }

    @PutMapping("/{id}")
    fun modifyTask(@PathVariable("id") id: Long) {
        TODO()
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable("id") id: Long) {
        TODO()
    }

    @GetMapping
    fun list() {
        TODO()
    }

}
