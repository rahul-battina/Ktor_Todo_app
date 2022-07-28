package com.example.repository

import com.example.entities.ToDoDraft
import com.example.entities.Todo

interface ToDoRepository {

    fun getAllToDos():List<Todo>

    fun getToDo(id:Int):Todo?

    fun addToDo(draft: ToDoDraft): Todo

    fun removeToDo(id: Int): Boolean

    fun updateToDo(id: Int, draft: ToDoDraft): Boolean


}