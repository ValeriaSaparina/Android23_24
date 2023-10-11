package com.example.homework1.Base

import androidx.fragment.app.Fragment
import com.example.homework1.R

open class BaseFragment : Fragment() {
    protected val firstFragmentContainerId: Int = R.id.first_container_view
    protected val fourthFragmentContainerId: Int = R.id.fourth_container_view
}