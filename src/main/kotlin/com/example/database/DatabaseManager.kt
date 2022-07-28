package com.example.database

import com.example.entities.ToDoDraft
import com.example.entities.Todo
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class DatabaseManager {


    /*
    //config
     val hostname = "localhost"
     val databaseName = "ktor_todo"
     val username = "root"
     val password = "Word#1921"


    //database
    private val kTormDatabase: Database

    init {

        val jdbcUrl = "jdbc:mysql://$hostname:3306/$databaseName?user=$username&password=$password&useSSL=false"

        kTormDatabase = Database.connect(jdbcUrl)
    }

    */


    val kTormDatabase = Database.connect(
        url = "jdbc:mysql://localhost:3306/ktor_todo",

        user = "root",
        password = "Word#1921"
    )


    fun getAllTodos(): List<DBTodoEntity>{
        return kTormDatabase.sequenceOf(DBTodoTable).toList()
    }

    fun getToDo(id:Int): DBTodoEntity? {
        return kTormDatabase.sequenceOf(DBTodoTable).
        firstOrNull { it.id eq id }
    }

    fun addToDo(draft:ToDoDraft): Todo{
        val insertedID = kTormDatabase.insertAndGenerateKey(DBTodoTable){
            set(DBTodoTable.title, draft.title)
            set(DBTodoTable.done, draft.done)
        } as Int


        return Todo(insertedID, draft.title, draft.done)
    }

    fun updateToDo(id: Int, draft: ToDoDraft): Boolean{
        val updatedRows = kTormDatabase.update(DBTodoTable){
            set(DBTodoTable.title, draft.title)
            set(DBTodoTable.done, draft.done)
            where {
                it.id eq id
            }

        }
        return updatedRows > 0

    }

    fun removeToDo(id:Int): Boolean{
        val deletedRows = kTormDatabase.delete(DBTodoTable){
            it.id eq id
        }

        return deletedRows > 0

    }


}