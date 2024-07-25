package com.example.umc_stepper.utils

import android.app.Application
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: GlobalApplication
            private set

        fun loadImage(imageView: ImageView, source: Any) {
            Glide.with(instance)
                .load(source)
                .into(imageView)
        }

        fun loadProfileImage(imageView: ImageView, source: Any) {
            Glide.with(instance)
                .load(source)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(imageView)
        }
    }
}
