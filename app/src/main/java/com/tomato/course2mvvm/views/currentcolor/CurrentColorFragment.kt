package com.tomato.course2mvvm.views.currentcolor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tomato.course2mvvm.databinding.FragmentCurrentColorBinding
import com.tomato.course2mvvm.views.base.BaseFragment
import com.tomato.course2mvvm.views.base.BaseScreen
import com.tomato.course2mvvm.views.base.screenViewModel

class CurrentColorFragment: BaseFragment() {
    class Screen: BaseScreen

    override val viewModel by screenViewModel<CurrentColorViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentCurrentColorBinding.inflate(inflater, container, false)
        viewModel.currentColor.observe(viewLifecycleOwner) {
            binding.colorView.setBackgroundColor(it.value)
        }

        binding.changeColorButton.setOnClickListener {
            viewModel.changeColor()
        }

        return binding.root
    }
}