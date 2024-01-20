package com.example.homework4_1.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.homework4_1.Task
import com.example.homework4_1.TaskAdapter
import com.example.homework4_1.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val taskList = ArrayList<Task>()
    private val adapter = TaskAdapter(taskList, this::onClick)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvTask.adapter = adapter
        initListener()
    }

    private fun initListener() {
        binding.btnAddTask.setOnClickListener {
            adapter.addText(Task("Blank task", false, adapter.itemCount))
        }
        setFragmentResultListener(TaskEditFragment.REQUEST_KEY) { _, bundle ->
            val result = TaskEditFragmentArgs.fromBundle(bundle)
            if (result.task.text.isNotEmpty()){
                if (result.finished){
                    taskList.add(
                        Task(
                            result.task.text,
                            result.task.finished,
                            taskList.size
                        )
                    )
                }else{
                    taskList[result.task.position] = result.task
                    binding.rvTask.adapter?.notifyItemChanged(result.task.position)
                }
            }
        }
    }

    private fun onClick(position: Int){
        findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToTaskEditFragment(task = taskList[position], finished = false))
    }
}