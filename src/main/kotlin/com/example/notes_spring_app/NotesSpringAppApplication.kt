package com.example.notes_spring_app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NotesSpringAppApplication

fun main(args: Array<String>) {
	runApplication<NotesSpringAppApplication>(*args)
}
