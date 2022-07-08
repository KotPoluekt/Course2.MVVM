package com.tomato.course2mvvm.views

interface UiActions {

    //display simple toast message
    fun toast(messge: String)

    // get string resource content by id
    fun getString(messageRes: Int, vararg args: Any): String
}