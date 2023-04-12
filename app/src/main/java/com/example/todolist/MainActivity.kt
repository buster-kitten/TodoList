package com.example.todolist

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.db.AppDatabase
import com.example.todolist.db.ToDoDao
import com.example.todolist.db.ToDoEntity

class MainActivity : AppCompatActivity(),OnItemLongClickListner {

    private lateinit var binding: ActivityMainBinding

    //데이터베이스 관련 변수 선언, 할 일 리스트를 담아둘 todoList 선언
    private lateinit var db : AppDatabase
    private lateinit var toDoDao: ToDoDao
    private lateinit var todoList : ArrayList<ToDoEntity>
    //리사이클러뷰 어댑터를 변수로 선언
    private lateinit var adapter: TodoRecyclerViewAdapter

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

        //AppDatabase 객체를 생성해서 ToDoDao를 불러오고 Dao를 활용해서 MainActovoty에서 할 일 조회
        //DB 인스턴스를 가져오고 DB 작업을 할 수 있는 DAO를 가져옴
        db = AppDatabase.getInstance(this)!!
        toDoDao = db.getTodoDao()

        //할일리스트 가져오기
        getAllTodoList()

    }

    //백그라운드 스레드에서 DB관련작업
    private fun getAllTodoList() {
        Thread {
            todoList = ArrayList(toDoDao.getAll())
            setRecyclerView()
        }.start()
    }

    //getAllTodoList()함수에서 리스트를 가져온 후 호출, 리사이클러뷰 설정
    private fun setRecyclerView() {
        //리사이클러뷰 설정
        runOnUiThread {
            adapter = TodoRecyclerViewAdapter(todoList,this)
            //어댑터 객체 할당
            binding.recyclerView.adapter = adapter
            //리사이클러뷰 어댑터로 위에서 만든 어댑터 설정
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            //레이아웃 매니저 설정
        }
    }

    override fun onRestart() {
        //액티비티가 멈췄다가 다시 시작할 때 실행
        super.onRestart()
        getAllTodoList()
    }

    //OnItemLongClickListener 구현
    override fun onLongClick(position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("할 일 삭제")              //제목
        builder.setMessage("정말 삭제하시겠습니까?")  //내용
        builder.setNegativeButton("취소",null)    //취소
        builder.setPositiveButton("확인",object : DialogInterface.OnClickListener {
            //확인
            override fun onClick(p0: DialogInterface?, p1:Int) {
                deleteTodo(position)
            }
        })
        builder.show()
    }

    //Room 데이터베이스에서 해당 할일을 삭제하고 todoList를 업데이트
    private fun deleteTodo(position: Int) {
        Thread {
            toDoDao.deleteTodo(todoList[position])  //DB삭제
            todoList.removeAt(position)
            runOnUiThread {
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }
}