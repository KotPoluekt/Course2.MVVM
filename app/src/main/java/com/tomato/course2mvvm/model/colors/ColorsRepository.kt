package com.tomato.course2mvvm.model.colors

import com.tomato.course2mvvm.model.Repository

typealias ColorListener = (NamedColor) -> Unit

interface ColorsRepository: Repository {

    var currentColor: NamedColor

    fun getAvailableColors(): List<NamedColor>

    fun getById(id: Long): NamedColor

    fun addListener(listener: ColorListener)

    fun removeListener(listener: ColorListener)
}