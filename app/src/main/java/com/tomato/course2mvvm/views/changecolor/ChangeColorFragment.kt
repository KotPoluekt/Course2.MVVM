package com.tomato.course2mvvm.views.changecolor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.GridLayoutAnimationController
import android.widget.GridView
import androidx.recyclerview.widget.GridLayoutManager
import com.tomato.course2mvvm.R
import com.tomato.course2mvvm.databinding.FragmentChangeColorBinding
import com.tomato.course2mvvm.views.HasScreenTitle
import com.tomato.course2mvvm.views.base.BaseFragment
import com.tomato.course2mvvm.views.base.BaseScreen
import com.tomato.course2mvvm.views.base.screenViewModel

class ChangeColorFragment: BaseFragment(), HasScreenTitle {
    class Screen(val currentColorId: Long): BaseScreen

    override val viewModel by screenViewModel<ChangeColorViewModel>()

    override fun getScreenTitle(): String? = viewModel.screenTitle.value

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentChangeColorBinding.inflate(inflater, container, false)

        val adapter = ColorsAdapter(viewModel)
        setupLayoutManager(binding, adapter)

        binding.saveButton.setOnClickListener { viewModel.onSavePressed() }
        binding.cancelButton.setOnClickListener { viewModel.onCancelPressed() }

        viewModel.colorsList.observe(viewLifecycleOwner) {
            adapter.items = it
        }

        viewModel.screenTitle.observe(viewLifecycleOwner) {
            notifyScreenUpdates()
        }

        return binding.root
    }

    private fun setupLayoutManager(binding: FragmentChangeColorBinding, adapter: ColorsAdapter) {
        // waiting for list width
        binding.colorsRecyclerView.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.colorsRecyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val width = binding.colorsRecyclerView.width
                val itemWidth = resources.getDimensionPixelSize(R.dimen.item_width)
                val columns = width / itemWidth
                binding.colorsRecyclerView.adapter = adapter
                binding.colorsRecyclerView.layoutManager = GridLayoutManager(requireContext(), columns)
            }
        })
    }

}