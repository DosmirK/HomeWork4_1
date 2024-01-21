package com.example.homework4_1.ui.home.content.completed

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.homework4_1.databinding.FragmentCompletedTasksBinding
import com.example.homework4_1.ui.home.content.tasks.CompletedTasksViewHolder
import com.example.homework4_1.ui.home.content.tasks.Task
import com.example.homework4_1.ui.home.content.tasks.TaskAdapter
import com.example.homework4_1.ui.home.content.tasks.TaskLongViewHolder
import com.example.homework4_1.ui.home.content.tasks.TaskUrgentViewHolder

class CompletedTasksFragment(
    private val taskList: ArrayList<Task>,
    private val onClick: (position: Int) -> Unit,
    private val onItemSwipe: (position: Int) -> Unit
) : Fragment() {

    private var _binding: FragmentCompletedTasksBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompletedTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        binding.rvTasks.adapter?.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvTasks.adapter = TaskAdapter(taskList, onClick)
        initListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initListener() {
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                when (viewHolder) {
                    is TaskUrgentViewHolder -> onItemSwipe(viewHolder.taskPosition)
                    is CompletedTasksViewHolder -> onItemSwipe(viewHolder.taskPosition)
                    is TaskLongViewHolder -> onItemSwipe(viewHolder.taskPosition)
                }
                binding.rvTasks.adapter?.notifyDataSetChanged()
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvTasks)
    }
}