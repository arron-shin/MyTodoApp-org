package me.jungseob.apps.mytodoapp.controller

import me.jungseob.apps.mytodoapp.service.TaskService
import me.jungseob.apps.mytodoapp.service.model.Task
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable
import reactor.core.publisher.Mono

@Controller
class TaskViewController(
    private val taskService: TaskService
) {

    @GetMapping("/")
    fun homeView(): Mono<String> {
        return Mono.just("home")
    }

    @GetMapping("/tasks")
    suspend fun taskListView(model: Model): String {
        val tasks: List<Task> = taskService.list()
        model.addAttribute("tasks", tasks)
        return "task-list"
    }

    @GetMapping("/tasks/{id}")
    suspend fun taskUpdateView(@PathVariable id: Long, model: Model): String {
        val task = taskService.getTask(id)
        model.addAttribute("task", task)
        return "task-update"
    }

    @GetMapping("/tasks/new")
    fun taskAddView(): Mono<String> {
        return Mono.just("task-new")
    }
}
