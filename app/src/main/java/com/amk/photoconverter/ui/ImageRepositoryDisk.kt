package com.amk.photoconverter.ui

import android.content.Context
import android.net.Uri
import com.amk.photoconverter.mvp.model.Image
import com.amk.photoconverter.mvp.model.ImageRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File


class ImageRepositoryDisk(private val context: Context?) :
    ImageRepository {

    override fun getImage(file: File): Observable<Image> = Observable.create<Image> { emitter ->
        val imageStream = context?.contentResolver?.openInputStream(Uri.fromFile(file))
        imageStream?.let { stream ->
            val byteArray = stream.buffered().use { it.readBytes() }
            emitter.onNext(Image(byteArray))
        }
            ?: emitter.onError(IllegalArgumentException())
    }
        .subscribeOn(Schedulers.io())
}