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

    @GetMapping("/todo/main")
    fun mainView(): String {
        return "todo-main";
    }

    @GetMapping("/todo/tasks/{id}")
    fun taskDetailView(@PathVariable id: String): String {
        return "task-detail";
    }

    @GetMapping("/todo/tasks/new")
    fun taskPostView(): String {
        return "task-new";
    }

    @GetMapping("/todo/tasks/{id}/modify")
    fun taskModifyView(@PathVariable id: String): String {
        return "task-modify";
    }
}
