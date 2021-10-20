package me.jungseob.apps.mytodoapp.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class ViewController {

    @GetMapping("/")
    fun homeView(): String {
        return "home";
    }

    @GetMapping("/tasks")
    fun taskListView(): String {
        return "task-list";
    }

    @GetMapping("/tasks/{id}")
    fun taskUpdateView(@PathVariable id: Long): String {
        return "task-update";
    }

    @GetMapping("/tasks/new")
    fun taskAddView(): String {
        return "task-new";
    }
}
