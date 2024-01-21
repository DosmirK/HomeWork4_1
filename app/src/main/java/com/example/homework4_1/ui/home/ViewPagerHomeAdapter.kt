package com.example.homework4_1.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.homework4_1.ui.home.content.longs.LongTaskFragment
import com.example.homework4_1.ui.home.content.tasks.TaskFragment
import com.example.homework4_1.ui.home.content.urgent.UrgentTaskFragment

class ViewPagerHomeAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val fragments: List<Fragment>
): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
