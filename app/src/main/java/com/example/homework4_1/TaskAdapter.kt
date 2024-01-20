package com.example.homework4_1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.homework4_1.databinding.ItemTaskBinding

class TaskAdapter(
    private val taskList: ArrayList<Task>,
    private val onClick: (position: Int) -> Unit
) : Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.onBind(taskList[position])
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }

    fun addText(text: Task) {
        taskList.add(text)
        notifyItemChanged(taskList.lastIndex)
    }
}

class TaskViewHolder(private val binding: ItemTaskBinding) : ViewHolder(binding.root){

    fun onBind(task: Task){
        binding.tvTask.text = task.text
        binding.taskFinish.isChecked = task.finished
    }
}