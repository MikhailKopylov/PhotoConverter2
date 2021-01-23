package com.amk.photoconverter.ui.navigation

import com.amk.photoconverter.ui.fragments.ConverterFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class ConvertPhotoStartScreen : SupportAppScreen() {
        override fun getFragment() = ConverterFragment.newInstance()
    }
}