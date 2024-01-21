package com.example.homework4_1.ui.home.content.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.homework4_1.databinding.ItemAllTaskBinding
import com.example.homework4_1.databinding.ItemCompletedTasksBinding
import com.example.homework4_1.databinding.ItemLongTaskBinding
import com.example.homework4_1.databinding.ItemUrgentTaskBinding

class TaskAdapter(
    private val taskList: ArrayList<Task>,
    private val onClick: (position: Int) -> Unit
) : Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when(viewType) {
            TypeOfTask.URGENT_TASKS.id -> return TaskUrgentViewHolder(
                ItemUrgentTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            TypeOfTask.LONG_TASKS.id -> return TaskLongViewHolder(
                ItemLongTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            TypeOfTask.ALL_TASKS.id -> return AllTasksViewHolder(
                ItemAllTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> return CompletedTasksViewHolder(
                ItemCompletedTasksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return taskList[position].type.id
    }
    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is TaskUrgentViewHolder -> holder.onBind(taskList[position])
            is TaskLongViewHolder -> holder.onBind(taskList[position])
            is AllTasksViewHolder -> holder.onBind(taskList[position])
            else -> (holder as CompletedTasksViewHolder).onBind(taskList[position])
        }
        holder.itemView.setOnClickListener {
            onClick(taskList[position].position)
        }
    }

    fun addText(text: Task) {
        taskList.add(text)
        notifyItemChanged(taskList.lastIndex)
    }
}

class TaskUrgentViewHolder(private val binding: ItemUrgentTaskBinding) : ViewHolder(binding.root){

    var taskPosition = 0
    fun onBind(task: Task) {
        binding.tvTaskUrgent.text = task.text
        taskPosition = task.position
    }
}

class TaskLongViewHolder(private val binding: ItemLongTaskBinding): ViewHolder(binding.root){

    var taskPosition = 0
    fun onBind(task: Task) {
        binding.tvTaskLong.text = task.text
        taskPosition = task.position
    }
}

class AllTasksViewHolder(private val binding: ItemAllTaskBinding): ViewHolder(binding.root){

    var taskPosition = 0
    fun onBind(task: Task) {
        binding.tvAllTasks.text = task.text
        taskPosition = task.position
    }
}

class CompletedTasksViewHolder(private val binding: ItemCompletedTasksBinding): ViewHolder(binding.root){

    var taskPosition = 0
    fun onBind(task: Task) {
        binding.tvCompletedTasks.text = task.text
        taskPosition = task.position
    }
}