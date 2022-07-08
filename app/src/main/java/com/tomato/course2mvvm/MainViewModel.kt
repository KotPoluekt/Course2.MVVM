package com.tomato.course2mvvm

import android.app.Application
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import com.tomato.course2mvvm.utils.Event
import com.tomato.course2mvvm.utils.ResourceActions
import com.tomato.course2mvvm.views.Navigator
import com.tomato.course2mvvm.views.UiActions
import com.tomato.course2mvvm.views.base.BaseScreen
import com.tomato.course2mvvm.views.base.LiveEvent
import com.tomato.course2mvvm.views.base.MutableLiveEvent

const val ARG_SCREEN = "ARG_SCREEN"

class MainViewModel(application: Application):
    AndroidViewModel(application), Navigator, UiActions {

    val whenActivityActive = ResourceActions<MainActivity>()

    private val _result = MutableLiveEvent<Any>()
    val result: LiveEvent<Any> = _result

    override fun launch(screen: BaseScreen) = whenActivityActive {
        launchFragment(it, screen)
    }

    override fun goBack(result: Any?) = whenActivityActive {
        if (result != null) {
            _result.value = Event(result)
        }
        it.onBackPressed()
    }

    override fun toast(messge: String) {
        Toast.makeText(getApplication(), messge, Toast.LENGTH_SHORT).show()
    }

    override fun getString(messageRes: Int, vararg args: Any): String {
        return getApplication<App>().getString(messageRes, *args)
    }

    fun launchFragment(activity: MainActivity, screen: BaseScreen, addToBackStack: Boolean = true) {
        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
        fragment.arguments = bundleOf(ARG_SCREEN to screen)
        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(null)
        transaction
            .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onCleared() {
        super.onCleared()
        whenActivityActive.clear()
    }
}