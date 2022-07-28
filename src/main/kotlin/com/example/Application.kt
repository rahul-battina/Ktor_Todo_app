package com.example

import com.example.entities.ToDoDraft
import com.example.entities.Todo
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.repository.InMemoryToDoRepository
import com.example.repository.MySQLToDoRepository
import com.example.repository.ToDoRepository
import com.typesafe.config.ConfigException.Null
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*
import io.ktor.server.request.*
import com.example.database.DatabaseManager




fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        todoModule()
    }.start(wait = true)
}

fun Application.todoModule(){

    install(CallLogging)
    install(ContentNegotiation){
        gson {
            setPrettyPrinting()
        }
    }


    routing {


        val repository: ToDoRepository = MySQLToDoRepository()

        get("/") {

            call.respondText("Hello todolist")
        }

        get("/todos") {
            call.respond(repository.getAllToDos())
        }
        get("/todos/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null){
                call.respond(HttpStatusCode.BadRequest,
                    "id parameter has to be a number")

                return@get
            }

            val todo = repository.getToDo(id)

            if (todo == null){
                call.respond(HttpStatusCode.NotFound,
                "found no todo for the provided id $id")
            }
            else{
                call.respond(todo)
            }

        }

        post("/todos") {
            val toDoDraft = call.receive<ToDoDraft>()
            val todo = repository.addToDo(toDoDraft)
            call.respond(todo)
        }

        put("/todos/{id}") {

            val toDoDraft = call.receive<ToDoDraft>()
            val todoId = call.parameters["id"]?.toIntOrNull()
            if (todoId == null){

                call.respond(HttpStatusCode.BadRequest,
                "id parameter has to be a number")
                return@put
            }

            val updated = repository.updateToDo(todoId, toDoDraft)
            if (updated){
                call.respond(HttpStatusCode.OK)
            }
            else{
                call.respond(HttpStatusCode.NotFound,
                "No todo found with the id $todoId")
            }
        }
        delete("/todos/{id}") {
            val todoId = call.parameters["id"]?.toIntOrNull()
            if (todoId == null){

                call.respond(HttpStatusCode.BadRequest,
                    "id parameter has to be a number")
                return@delete
            }

            val removed = repository.removeToDo(todoId)
            if (removed){
                call.respond(HttpStatusCode.OK)
            }
            else{
                call.respond(HttpStatusCode.NotFound,
                "No todo found with the id $todoId")
            }
        }
    }
}


