package com.example.homework3

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    protected val fragmentContainerId = R.id.main_activity_container
}