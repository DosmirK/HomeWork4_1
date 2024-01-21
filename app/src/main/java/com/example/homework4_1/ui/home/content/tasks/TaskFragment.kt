package com.example.homework4_1.ui.home.content.tasks

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.homework4_1.databinding.FragmentTaskBinding

class TaskFragment(
    private val tasks: ArrayList<Task>,
    private val onClick: (position: Int) -> Unit,
    private val onItemSwipe: (position: Int) -> Unit
) : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvTask.adapter = TaskAdapter(tasks, onClick)
        initListener()
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        binding.rvTask.adapter?.notifyDataSetChanged()
    }
    private fun initListener() {
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT //or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN or ItemTouchHelper.UP
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
                binding.rvTask.adapter?.notifyDataSetChanged()
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvTask)
    }
}