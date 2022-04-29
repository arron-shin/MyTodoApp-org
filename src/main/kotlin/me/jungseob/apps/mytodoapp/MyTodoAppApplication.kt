package me.jungseob.apps.mytodoapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyTodoAppApplication

fun main(args: Array<String>) {
    runApplication<MyTodoAppApplication>(*args)
}
