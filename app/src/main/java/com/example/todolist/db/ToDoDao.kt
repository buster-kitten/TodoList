package com.example.todolist.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ToDoDao {
    @Query("SELECT * FROM ToDoEntity")
    fun getAll() : List<ToDoEntity>
    //ToDoEntity에서 모든 데이터를 불러옴

    @Insert
    fun insertTodo(todo : ToDoEntity)
    //ToDoEntity객체를 테이블에 삽입

    @Delete
    fun deleteTodo(todo : ToDoEntity)
    //특정 ToDoEntity객체를 테이블에서 삭제
}