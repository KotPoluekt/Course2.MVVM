package com.tomato.course2mvvm.views.base

import androidx.fragment.app.Fragment
import com.tomato.course2mvvm.MainActivity

abstract class BaseFragment: Fragment() {
    abstract val viewModel: BaseViewModel

    fun notifyScreenUpdates() {
        // if you have more than 1 activity - you should use a separate interface instead of direct
        // cast to MainActivity

        (requireActivity() as MainActivity).notifyScreenUpdates()
    }
}