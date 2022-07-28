package com.example.repository

import com.example.entities.ToDoDraft
import com.example.entities.Todo

class InMemoryToDoRepository: ToDoRepository {

    private val todos = mutableListOf<Todo>(
        Todo(1, "plan content for video #2", true),
        Todo(2,"Record video #2", false),
        Todo(3,"Upload video #2", false)
    )

    override fun getAllToDos(): List<Todo> {
        return todos
    }

    override fun getToDo(id: Int): Todo? {
        return todos.firstOrNull { it.id == id }
    }

    override fun addToDo(draft: ToDoDraft): Todo {
        val todo = Todo(
            id = todos.size + 1,
            title = draft.title,
            done = draft.done
        )
        todos.add(todo)
        return todo
    }

    override fun removeToDo(id: Int): Boolean {
        return todos.removeIf { it.id == id }
    }

    override fun updateToDo(id: Int, draft: ToDoDraft): Boolean {
        val todo = todos.firstOrNull { it.id == id }
            ?: return false

        todo.title = draft.title
        todo.done = draft.done

        return true


    }


}