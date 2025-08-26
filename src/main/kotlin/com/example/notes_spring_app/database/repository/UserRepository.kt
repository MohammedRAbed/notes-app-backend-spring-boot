package com.example.notes_spring_app.database.repository

import com.example.notes_spring_app.database.model.Note
import com.example.notes_spring_app.database.model.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, ObjectId> {
    fun findByEmail(email: String): User?
}
