package com.amk.photoconverter.ui.activity

import android.os.Bundle
import com.amk.photoconverter.R
import com.amk.photoconverter.mvp.presenter.MainPresenter
import com.amk.photoconverter.mvp.view.MainView
import com.amk.photoconverter.ui.App
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import com.amk.photoconverter.ui.BackButtonListener
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : MvpAppCompatActivity(), MainView {


    private val navigatorHolder = App.instance.navigationHolder
    private val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.container)

    private val presenter by moxyPresenter {
        MainPresenter(App.instance.router)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.let {
            if (it is BackButtonListener && it.pressedBackButton()) {
                return
            }
        }
        presenter.pressedBackButton()
    }
}