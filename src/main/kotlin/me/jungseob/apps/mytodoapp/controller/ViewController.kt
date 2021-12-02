package me.jungseob.apps.mytodoapp.controller

import me.jungseob.apps.mytodoapp.service.TaskService
import me.jungseob.apps.mytodoapp.service.model.Task
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class ViewController(
    private val taskService: TaskService
) {

    @GetMapping("/")
    fun homeView(): String {
        return "home";
    }

    @GetMapping("/tasks")
    fun taskListView(model: Model): String {

        val tasks: List<Task> = taskService.list()
        model.addAttribute("tasks", tasks);

        return "task-list";
    }

    @GetMapping("/tasks/{id}")
    fun taskUpdateView(@PathVariable id: Long, model: Model): String {

        val task = taskService.getTask(id)
        model.addAttribute("task", task)

        return "task-update";
    }

    @GetMapping("/tasks/new")
    fun taskAddView(): String {
        return "task-new";
    }
}
