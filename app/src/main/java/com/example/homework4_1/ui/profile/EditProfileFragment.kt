package com.example.homework4_1.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homework4_1.utils.App
import com.example.homework4_1.databinding.FragmentEditProfileBinding
import com.example.homework4_1.utils.loadImage


class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private var selectedImageUri: Uri? = null
    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                handleImageResult(data)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()

    }

    private fun initListener() {

        binding.ivProfile.setOnClickListener {
            openImageChooser()
        }

        binding.btnSave.setOnClickListener {
            (requireContext().applicationContext as App).mySharedPreferences?.saveName(
                binding.etName.text.toString()
            )

            (requireContext().applicationContext as App).mySharedPreferences?.saveImage(
                selectedImageUri.toString()
            )
            findNavController().navigateUp()
        }
    }

    private fun openImageChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        getContent.launch(intent)
    }

    private fun handleImageResult(data: Intent?) {
        if (data != null && data.data != null) {
            selectedImageUri = data.data
            binding.ivProfile.loadImage(selectedImageUri.toString())
        }
    }

    companion object{
        const val REQUEST_KEY = "EditProfileFragment_REQUEST_KEY"

    }
}