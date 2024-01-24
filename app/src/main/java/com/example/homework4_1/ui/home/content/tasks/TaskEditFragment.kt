package com.example.homework4_1.ui.home.content.tasks

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homework4_1.databinding.FragmentTaskEditBinding
import kotlinx.parcelize.Parcelize

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
        when (args.task.type) {
            TypeOfTask.URGENT_TASKS -> binding.rbInUrgentTask.isChecked = true
            TypeOfTask.LONG_TASKS -> binding.rbLongTask.isChecked = true
            TypeOfTask.COMPLETED -> binding.rbCompletedTask.isChecked = true
            else -> {
                binding.rbInUrgentTask.isChecked = true
            }
        }
    }
    @Parcelize
    data class TaskEditModel(
        val task: Task,
        val new: Boolean,
        val taskType: TypeOfTask
            ): Parcelable

    private fun initListener() {
        binding.btnSave.setOnClickListener {
            setFragmentResult(
                requestKey = REQUEST_KEY,
                result = bundleOf(
                    "task_result" to TaskEditModel(
                        Task(
                            text = binding.etTask.text.toString(),
                            position = args.task.position,
                            when (binding.rbGroup.checkedRadioButtonId) {
                                binding.rbInUrgentTask.id -> TypeOfTask.URGENT_TASKS
                                binding.rbCompletedTask.id -> TypeOfTask.COMPLETED
                                else -> TypeOfTask.LONG_TASKS
                            }
                        ),
                        new = args.new,
                        taskType = args.lastType
                    )
                )
            )
            findNavController().navigateUp()
        }
    }

    companion object{
       const val REQUEST_KEY = "TaskEditFragment_REQUEST_KEY"
    }
}