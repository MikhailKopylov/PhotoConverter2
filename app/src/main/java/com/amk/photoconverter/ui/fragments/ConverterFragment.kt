package com.amk.photoconverter.ui.fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.amk.photoconverter.R
import com.amk.photoconverter.mvp.model.Image
import com.amk.photoconverter.mvp.presenter.ConverterPresenter
import com.amk.photoconverter.mvp.view.ConverterView
import com.amk.photoconverter.ui.App
import com.amk.photoconverter.ui.ConverterImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_convert.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import com.amk.photoconverter.ui.BackButtonListener


class ConverterFragment : MvpAppCompatFragment(), ConverterView, BackButtonListener {

    companion object {
        private const val PICK_IMAGE = 1

        @JvmStatic
        fun newInstance() = ConverterFragment()

    }

    private val presenter by moxyPresenter {
        ConverterPresenter(
            App.instance.router,
            AndroidSchedulers.mainThread(),
            ConverterImpl(context)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_convert, container, false)
    }

    override fun init() {
        select_image_button.setOnClickListener {
            presenter.selectPhoto()
            imageView.setImageBitmap(null)
        }

        cancel_converting_button.setOnClickListener {
            presenter.cancelingConverting()
        }
    }

    override fun showConvertedInProgress() {
        progress_text_view.visibility = View.VISIBLE
        cancel_converting_button.isClickable = true
        select_image_button.isClickable = false

    }

    override fun hideConvertedInProgress() {
        progress_text_view.visibility = View.INVISIBLE
        cancel_converting_button.isClickable = false
        select_image_button.isClickable = true
    }

    override fun successMessage() {
        Toast.makeText(context, "Конвертация успешно произведена!", Toast.LENGTH_SHORT).show()
    }

    override fun failureMessage() {
        Toast.makeText(context, "Конвертация не выполнена!", Toast.LENGTH_SHORT).show()
    }

    override fun selectPhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE)
    }

    override fun setImage(image: Image) {
        val bitmap =
            BitmapFactory.decodeByteArray(image.byteArrayImage, 0, image.byteArrayImage.size)
        imageView.setImageBitmap(bitmap)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_IMAGE -> {
                data?.data.let { uri ->
                    val byteArray: ByteArray? = uri?.let { it ->
                        context?.contentResolver?.openInputStream(it)?.buffered()
                            ?.use { it.readBytes() }
                    }
                    byteArray?.let {
                        presenter.convertImage(Image(it))
                    }
                }
            }
        }
    }

    override fun pressedBackButton() = presenter.backClicked()
}
