package me.jungseob.apps.mytodoapp

import javax.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyTodoAppApplication {
    @Value("\${my-app.welcome-message}")
    var myAppWelcomeMessage: String? = null

    @PostConstruct
    fun printMyAppWelcomeMessage() { println(myAppWelcomeMessage) }
}

fun main(args: Array<String>) {
    runApplication<MyTodoAppApplication>(*args)
}
