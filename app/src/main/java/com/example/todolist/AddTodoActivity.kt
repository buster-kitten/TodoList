package com.example.todolist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityAddTodoBinding
import com.example.todolist.db.AppDatabase
import com.example.todolist.db.ToDoDao
import com.example.todolist.db.ToDoEntity

class AddTodoActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddTodoBinding
    lateinit var db: AppDatabase
    lateinit var todoDao: ToDoDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()

        binding.btnCompletion.setOnClickListener {
            insertTodo()
        }
    }

    //@desc 할 일 추가 함수

    private fun insertTodo() {

        val todoTitle = binding.edtTitle.text.toString()
        var todoImportance = binding.radioGroup.checkedRadioButtonId
        //할 일의 중요도

        //어떤 버튼이 눌렸는지 확인하고 값을 지정
        when(todoImportance) {
            R.id.btn_high -> {
                todoImportance = 1
            }

            R.id.btn_middle -> {
                todoImportance = 2
            }

            R.id.btn_low -> {
                todoImportance = 3
            }

            else -> {
                todoImportance = -1
            }
        }
            //중요도가 선택되지 않거나, 제목이 작성되지 않는지 체크
        if(todoImportance == -1 || todoTitle.isBlank()) {
            Toast.makeText(this,"모든 항목을 입력해 주세요.",Toast.LENGTH_SHORT).show()
        } else {
            Thread {
                todoDao.insertTodo(ToDoEntity(null, todoTitle, todoImportance))
                runOnUiThread {
                    //아래 작업은 UI 스레드에서 실행해야함
                    Toast.makeText(this,"추가되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                    //AddTodoActivity 종료, 다시 MainActivity로 복귀
                }
            }.start()
        }
   }
}