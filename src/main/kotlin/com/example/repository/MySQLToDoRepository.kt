package com.example.repository

import com.example.database.DatabaseManager
import com.example.entities.ToDoDraft
import com.example.entities.Todo

class MySQLToDoRepository: ToDoRepository {

    private val database = DatabaseManager()

    override fun getAllToDos(): List<Todo> {
        return database.getAllTodos().
            map { Todo(it.id, it.title, it.done) }
    }

    override fun getToDo(id: Int): Todo? {
        return database.getToDo(id)
            ?.let { Todo(it.id, it.title, it.done) }
    }

    override fun addToDo(draft: ToDoDraft): Todo {

        return database.addToDo(draft)
    }

    override fun removeToDo(id: Int): Boolean {
        return database.removeToDo(id)
    }

    override fun updateToDo(id: Int, draft: ToDoDraft): Boolean {
        return database.updateToDo(id, draft)
    }

}