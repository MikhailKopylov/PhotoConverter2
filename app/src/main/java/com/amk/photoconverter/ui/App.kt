package com.amk.photoconverter.ui

import android.app.Application
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class App : Application() {

    val navigationHolder
        get() = cicerone.navigatorHolder

    val router
        get() = cicerone.router

    companion object {
        lateinit var instance: App
    }

    private val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


}