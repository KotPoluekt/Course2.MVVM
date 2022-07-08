package com.tomato.course2mvvm.views.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomato.course2mvvm.utils.Event

typealias LiveEvent<T> = LiveData<Event<T>>
typealias MutableLiveEvent<T> = MutableLiveData<Event<T>>

open class BaseViewModel: ViewModel() {

    // overrirde it if you want to listen for results from other screens
    open fun onResult(result: Any) {

    }
}