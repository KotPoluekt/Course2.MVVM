package com.tomato.course2mvvm.views

import com.tomato.course2mvvm.views.base.BaseScreen

interface Navigator {

    // launch a screen on the top of back stack
    fun launch(screen: BaseScreen)

    // go neck to the previous screen and optionally send some result
    fun goBack(result: Any? = null)
}