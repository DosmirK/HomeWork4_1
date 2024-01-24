package com.example.homework4_1.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homework4_1.utils.App
import com.example.homework4_1.databinding.FragmentProfileBinding
import com.example.homework4_1.utils.loadImage

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvProfileName.text = (requireContext().applicationContext as App).mySharedPreferences?.getName().toString()

        val img: String = (requireContext().applicationContext as App).mySharedPreferences?.getImage().toString()

        binding.ivProfile.loadImage(img)

        initListener()
    }

    private fun initListener() {
        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionNavigationProfileToEditProfileFragment())
        }
    }
}