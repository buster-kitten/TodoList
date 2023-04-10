package com.example.todolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ToDoEntity::class), version=1)
//@Database 어노테이션에서 데이터베이스와 관련된 모든 Entity를 나열
abstract class AppDatabase : RoomDatabase() {
//RoomDatabase를 상속하는 추상클래스여야 함
    abstract fun getTodoDao() : ToDoDao
    //DAO를 반환하고 인수가 존재하지 않는 추상함수 존재

    companion object {
        val databaseName = "db_todo"
        var appDatabase : AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase? {
            if(appDatabase == null) {
                appDatabase = Room.databaseBuilder(context,AppDatabase::class.java, databaseName).build()
            }
            return appDatabase
        }
    }
}
