package com.example.homework2.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.homework2.fragments.QuestionnaireFragment

class FragmentAdapter(
    manager: FragmentManager,
    lifecycle: Lifecycle,
    private val questionCount: Int
) :
    FragmentStateAdapter(manager, lifecycle) {

    override fun getItemCount(): Int = questionCount

    override fun createFragment(position: Int): Fragment {
        return QuestionnaireFragment.newInstance(position, questionCount)
    }

}
