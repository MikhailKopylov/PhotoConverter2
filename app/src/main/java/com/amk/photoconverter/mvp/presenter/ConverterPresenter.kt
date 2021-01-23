package com.amk.photoconverter.mvp.presenter

import com.amk.photoconverter.mvp.model.Converter
import com.amk.photoconverter.mvp.model.Image
import com.amk.photoconverter.mvp.view.ConverterView
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

class ConverterPresenter(
    private val router: Router,
    private val mainScheduler: Scheduler,
    private val converter: Converter,
) : MvpPresenter<ConverterView>() {

    //Для отписки при отмене
    private var converterDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun convertImage(image: Image) {
        converterDisposable = converter.convertImage(image)
            .observeOn(mainScheduler)
            .subscribe({
                viewState.hideConvertedInProgress()
                viewState.successMessage()
                viewState.setImage(it)
            }, {
                viewState.hideConvertedInProgress()
                viewState.failureMessage()
            })
    }

    fun selectPhoto() {
        viewState.selectPhoto()
        viewState.showConvertedInProgress()
    }

    fun cancelingConverting() {
        converterDisposable?.dispose()
        viewState.hideConvertedInProgress()
        viewState.failureMessage()
    }

    fun backClicked(): Boolean {
        router.exit()
        return true
    }
}