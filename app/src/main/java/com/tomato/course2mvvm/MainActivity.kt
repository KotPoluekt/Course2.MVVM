package com.tomato.course2mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.tomato.course2mvvm.views.HasScreenTitle
import com.tomato.course2mvvm.views.base.BaseFragment
import com.tomato.course2mvvm.views.currentcolor.CurrentColorFragment

class MainActivity : AppCompatActivity() {

    private val activityViewModel by viewModels<MainViewModel> { ViewModelProvider.AndroidViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            activityViewModel.launchFragment(
                activity = this,
                screen = CurrentColorFragment.Screen(),
                addToBackStack = false
            )
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, false)
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        activityViewModel.whenActivityActive.resource = this
    }

    override fun onPause() {
        super.onPause()
        activityViewModel.whenActivityActive.resource = null
    }

    fun notifyScreenUpdates() {
        val f = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (supportFragmentManager.backStackEntryCount > 0) {
            //if - more than 1 screen - show back button in the toolbar
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        if (f is HasScreenTitle && f.getScreenTitle() != null) {
            // display custom toolbar title
            supportActionBar?.title = f.getScreenTitle()
        } else {
            supportActionBar?.title = "Simple MVVM example"
        }

        val result = activityViewModel.result.value?.getValue() ?: return
        if (f is BaseFragment) {
            // has result that can be delivered to the screen's view model
            f.viewModel.onResult(result)
        }
    }

    private val fragmentCallbacks = object: FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            notifyScreenUpdates()
        }
    }
}