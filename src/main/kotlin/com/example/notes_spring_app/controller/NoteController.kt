package com.example.notes_spring_app.controller

import com.example.notes_spring_app.database.model.Note
import com.example.notes_spring_app.database.repository.NoteRepository
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/notes")
class NoteController(
    private val repository: NoteRepository
) {

    data class NoteRequest(
        val id: String?,
        val title: String,
        val content: String,
        val color: Long,
        val ownerId: String?
    )

    data class NoteResponse(
        val id: String?,
        val title: String,
        val content: String,
        val color: Long,
        val createdAt: Instant
    )

    private fun Note.toResponse() : NoteResponse {
        return NoteResponse(
            id = id.toHexString(),
            title = title,
            content = content,
            color = color,
            createdAt = createdAt
        )
    }

    @PostMapping
    fun save(
        @RequestBody noteBody: NoteRequest
    ): NoteResponse {
        val note = repository.save(
            Note(
                id = noteBody.id?.let { ObjectId(it) } ?: ObjectId.get(),
                title = noteBody.title,
                content = noteBody.content,
                color = noteBody.color,
                createdAt = Instant.now(),
                ownerId = ObjectId()
            )
        )
        return note.toResponse()
    }

    @DeleteMapping(path = ["/{id}"])
    fun deleteById(@PathVariable id: String) {
        repository.deleteById(ObjectId(id))
    }

    @GetMapping
    fun findOwnerById(
        @RequestParam(required = true) ownerId: String
    ): List<NoteResponse> {
        return repository.findByOwnerId(ObjectId(ownerId)).map {
            it.toResponse()
        }
    }
}