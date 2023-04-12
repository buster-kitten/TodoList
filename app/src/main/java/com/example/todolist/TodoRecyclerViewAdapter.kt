package com.example.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemTodoBinding
import com.example.todolist.db.ToDoEntity

class TodoRecyclerViewAdapter(private val todoList : ArrayList<ToDoEntity>,private val listener : OnItemLongClickListner) : RecyclerView.Adapter<TodoRecyclerViewAdapter.MyViewHolder>() {
    //어댑터 객체 생성 시 todoList를 인수로 받음, OnItemLongClickListener를 인수로 추가

    inner class MyViewHolder(binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        val tv_importance = binding.tvImportance
        val tv_title = binding.tvTitle

        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //MyViewHolder클래스에서 만든 뷰홀더 객체 생성
        val binding : ItemTodoBinding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //onCreateViewHolder에서 받은 데이터를 뷰홀더 객체에 어떻게 넣어줄지 결정
        val todoData = todoList[position]
        //중요도에 따라 색상을 변경
        when(todoData.importance) {
            1 -> {
                holder.tv_importance.setBackgroundResource(R.color.red)
            }
            2 -> {
                holder.tv_importance.setBackgroundResource(R.color.yellow)
            }
            3 -> {
                holder.tv_importance.setBackgroundResource(R.color.green)
            }
        }
        //중요도에 따라 텍스트 변경
        holder.tv_importance.text = todoData.importance.toString()
        //할 일의 제목 변경
        holder.tv_title.text = todoData.title

        //할일이 길게 클릭되었을 때 리스너 함수 실행
        holder.root.setOnLongClickListener {
            listener.onLongClick(position)
            false
        }
    }

    override fun getItemCount(): Int {
        //데이터가 몇 개인지 변환, 리사이클러뷰 아이템 개수는 할 일 리스트 크기
        return todoList.size
    }
}