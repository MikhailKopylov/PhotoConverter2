package com.amk.photoconverter.mvp.presenter

import com.amk.photoconverter.mvp.view.MainView
import com.amk.photoconverter.ui.navigation.Screens
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

class MainPresenter(private val router: Router):MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(Screens.ConvertPhotoStartScreen())
    }

    fun pressedBackButton() {
        router.exit()
    }
}