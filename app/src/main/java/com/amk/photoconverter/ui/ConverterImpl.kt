package com.amk.photoconverter.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.amk.photoconverter.mvp.model.Converter
import com.amk.photoconverter.mvp.model.Image
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream

const val DELAY = 3000L

class ConverterImpl(private val context: Context?) : Converter {
    override fun convertImage(image: Image): Single<Image> = Single.create<Image> { emitter ->
        context?.let {
            //Задержка в обработке файла и прерывание при отмене
            try {
                Thread.sleep(DELAY)
            } catch (e: InterruptedException) {
                return@create
            }

            //конвертация в bitMap и запись на диск
            val bitmap =
                BitmapFactory.decodeByteArray(image.byteArrayImage, 0, image.byteArrayImage.size)
            val file = File(context.getExternalFilesDir(null), "resultConverting.png")
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            bitmap.recycle()
            stream.close()

            //Получаем конвертированный файл
            ImageRepositoryDisk(context)
                .getImage(file)
                .observeOn(Schedulers.io())
                .subscribe({
                    emitter.onSuccess(it)
                }, {
                    emitter.onError(it)
                })
        }
    }.subscribeOn(Schedulers.io())


}