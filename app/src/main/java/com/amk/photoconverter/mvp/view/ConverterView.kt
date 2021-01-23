package com.amk.photoconverter.mvp.view

import com.amk.photoconverter.mvp.model.Image
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleTagStrategy::class)
interface ConverterView:MvpView {

    fun failureMessage()
    fun successMessage()
    fun selectPhoto()
    fun setImage(image: Image)
    fun init()
    fun showConvertedInProgress()
    fun hideConvertedInProgress()

}