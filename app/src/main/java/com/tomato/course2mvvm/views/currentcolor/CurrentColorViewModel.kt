package com.tomato.course2mvvm.views.currentcolor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tomato.course2mvvm.R
import com.tomato.course2mvvm.model.colors.ColorListener
import com.tomato.course2mvvm.model.colors.ColorsRepository
import com.tomato.course2mvvm.model.colors.NamedColor
import com.tomato.course2mvvm.views.Navigator
import com.tomato.course2mvvm.views.UiActions
import com.tomato.course2mvvm.views.base.BaseViewModel
import com.tomato.course2mvvm.views.changecolor.ChangeColorFragment

class CurrentColorViewModel(
    private val navigator: Navigator,
    private val uiActoins: UiActions,
    private val colorsRepository: ColorsRepository
): BaseViewModel() {

    private val _currentColor = MutableLiveData<NamedColor>()
    val currentColor: LiveData<NamedColor> = _currentColor

    private val colorListener: ColorListener = {
        _currentColor.postValue(it)
    }

    // --- example of listening results via model layer
    init {
        colorsRepository.addListener(colorListener)
    }

    override fun onCleared() {
        super.onCleared()
        colorsRepository.removeListener(colorListener)
    }

    override fun onResult(result: Any) {
        super.onResult(result)
        if (result is NamedColor) {
            val message = uiActoins.getString(R.string.changed_color, result.name)
            uiActoins.toast(message)
        }
    }

    fun changeColor() {
        val currentColor = _currentColor.value ?: return
        val screen = ChangeColorFragment.Screen(currentColor.id)
        navigator.launch(screen)
    }
}