package com.example.homework4_1.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.homework4_1.R
import com.example.homework4_1.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = arrayListOf<Fragment>(
            FirstFragment(),
            SecondFragment(),
            ThirdFragment()
        )

        val adapter = ViewPagerAdapter(
            requireActivity().supportFragmentManager,
            lifecycle,
            fragmentList
        )

        binding.vpOnboarding.adapter = adapter
        binding.dotsIndicator.attachTo(binding.vpOnboarding)

        initListener()
    }

    private fun initListener() {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.vp_onboarding)
        binding.btnSkip.setOnClickListener {
            viewPager?.currentItem = 2
        }
    }
}