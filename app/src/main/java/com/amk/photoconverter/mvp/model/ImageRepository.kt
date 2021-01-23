package com.amk.photoconverter.mvp.model

import io.reactivex.rxjava3.core.Observable
import java.io.File


interface ImageRepository {
    fun getImage(file: File): Observable<Image>
}