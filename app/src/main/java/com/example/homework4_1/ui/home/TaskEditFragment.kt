package com.example.homework4_1.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homework4_1.Task
import com.example.homework4_1.databinding.FragmentTaskEditBinding

class TaskEditFragment : Fragment() {

    private var _binding: FragmentTaskEditBinding? = null
    private val binding get() = _binding!!
    private val args: TaskEditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initView()
    }

    private fun initView() {
        binding.etTask.setText(args.task.text)
        binding.checkBox.isChecked = args.task.finished
    }

    private fun initListener() {
        binding.btnDone.setOnClickListener {
            setFragmentResult(
                REQUEST_KEY, TaskEditFragmentArgs(
                    Task(
                        text = binding.etTask.text.toString(),
                        finished = binding.checkBox.isChecked,
                        position = args.task.position
                    ),
                    finished = args.finished
                ).toBundle()
            )
            findNavController().navigateUp()
        }
    }

    companion object{
       const val REQUEST_KEY = "TaskEditFragment_REQUEST_KEY"
    }
}