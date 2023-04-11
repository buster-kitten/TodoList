package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //뷰바인딩 설정
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //인텐트를 사용해 다음 액티비티로 넘어가는 리스너 구현현
       binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }
    }
}