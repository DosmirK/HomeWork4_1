package com.example.homework4_1.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.homework4_1.R
import com.example.homework4_1.databinding.FragmentHomeBinding
import com.example.homework4_1.ui.home.content.completed.CompletedTasksFragment
import com.example.homework4_1.ui.home.content.longs.LongTaskFragment
import com.example.homework4_1.ui.home.content.tasks.Task
import com.example.homework4_1.ui.home.content.tasks.TaskEditFragment
import com.example.homework4_1.ui.home.content.tasks.TaskFragment
import com.example.homework4_1.ui.home.content.tasks.TypeOfTask
import com.example.homework4_1.ui.home.content.urgent.UrgentTaskFragment
import com.google.android.material.tabs.TabLayoutMediator
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val allTasks = loadData(ArrayList())
    private val urgentTasks = ArrayList(allTasks.filter { it.type == TypeOfTask.URGENT_TASKS })
    private val longTasks = ArrayList(allTasks.filter { it.type == TypeOfTask.LONG_TASKS })
    private val completedTasks = ArrayList(allTasks.filter { it.type == TypeOfTask.COMPLETED })

    private val fragments = listOf(
        TaskFragment(allTasks, this::onClick, this::onItemSwipe),
        UrgentTaskFragment(urgentTasks, this::onClick, this::onItemSwipe),
        LongTaskFragment(longTasks, this::onClick, this::onItemSwipe),
        CompletedTasksFragment(completedTasks, this::onClick, this::onItemSwipe)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeViewPager.adapter =
            ViewPagerHomeAdapter(childFragmentManager, lifecycle, fragments)
        binding.homeViewPager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, binding.homeViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> resources.getString(R.string.title_all_tasks)
                1 -> resources.getString(R.string.title_urgent_tasks)
                2 -> resources.getString(R.string.title_long_tasks)
                else -> resources.getString(R.string.title_completed_tasks)
            }
        }.attach()
        initListener()
    }

    private fun initListener() {
        binding.btnAddTask.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionNavigationHomeToTaskEditFragment(
                    Task(), true, TypeOfTask.URGENT_TASKS
                )
            )
        }

        setFragmentResultListener(TaskEditFragment.REQUEST_KEY) { _, bundle ->
            val result = bundle.getParcelable<TaskEditFragment.TaskEditModel>("task_result")
            if (result != null) {
                if (result.task.text.isNotEmpty()) {
                    if (result.new) {
                        result.task.position = allTasks.size
                        allTasks.add(result.task)
                        when (result.task.type) {
                            TypeOfTask.LONG_TASKS -> longTasks.add(result.task)
                            TypeOfTask.URGENT_TASKS -> urgentTasks.add(result.task)
                            else -> {completedTasks.add((result.task))}
                        }
                    } else {
                        allTasks[result.task.position] = result.task
                        if (result.taskType == result.task.type) {
                            when (result.task.type) {
                                TypeOfTask.LONG_TASKS ->
                                    longTasks[longTasks.indexOf(longTasks.find { it.position == result.task.position })] =
                                        result.task

                                TypeOfTask.URGENT_TASKS ->
                                    urgentTasks[urgentTasks.indexOf(urgentTasks.find { it.position == result.task.position })] =
                                        result.task

                                else -> {
                                    completedTasks[completedTasks.indexOf(completedTasks.find { it.position == result.task.position })] =
                                        result.task
                                }
                            }
                        } else {
                            when (result.taskType) {
                                TypeOfTask.LONG_TASKS ->
                                    longTasks.remove(longTasks.find { it.position == result.task.position })

                                TypeOfTask.URGENT_TASKS ->
                                    urgentTasks.remove(urgentTasks.find { it.position == result.task.position })

                                else -> {
                                    completedTasks.remove(completedTasks.find { it.position == result.task.position })
                                }
                            }
                            when (result.task.type) {
                                TypeOfTask.LONG_TASKS -> longTasks.add(result.task)
                                TypeOfTask.URGENT_TASKS -> urgentTasks.add(result.task)
                                else -> {completedTasks.add(result.task)}
                            }
                        }
                    }
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun onClick(position: Int) {
        findNavController().navigate(
            HomeFragmentDirections.actionNavigationHomeToTaskEditFragment(
                allTasks[position], new = false, lastType = allTasks[position].type
            )
        )
    }
    private fun onItemSwipe(position: Int) {
        when (allTasks[position].type) {
            TypeOfTask.LONG_TASKS -> longTasks.remove(allTasks[position])
            TypeOfTask.URGENT_TASKS -> urgentTasks.remove(allTasks[position])
            else -> {completedTasks.remove(allTasks[position])}
        }
        allTasks.removeAt(position)
        for ((index, task) in allTasks.withIndex()) {
            task.position = index
        }
    }
}
private fun loadData(tasks: ArrayList<Task>): ArrayList<Task> {
    tasks.add(
        Task(
            "Написать долгосрочную задачу.",
            position = tasks.size,
            type = TypeOfTask.LONG_TASKS
        )
    )
    tasks.add(
        Task(
            "Выполнить задачу.",
            position = tasks.size,
            type = TypeOfTask.COMPLETED
        )
    )
    tasks.add(
        Task(
            "Написать краткосрочную задачу.",
            position = tasks.size,
            type = TypeOfTask.URGENT_TASKS
        )
    )
    return tasks
}
