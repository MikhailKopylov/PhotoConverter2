package com.amk.photoconverter.mvp.model

import io.reactivex.rxjava3.core.Single

interface Converter {
    fun convertImage(image: Image): Single<Image>
}