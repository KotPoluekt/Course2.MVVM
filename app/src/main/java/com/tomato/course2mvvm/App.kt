package com.tomato.course2mvvm

import android.app.Application
import com.tomato.course2mvvm.model.colors.InMemoryColorsRepository

class App: Application() {

    val models = listOf<Any> (
        InMemoryColorsRepository()
    )
}