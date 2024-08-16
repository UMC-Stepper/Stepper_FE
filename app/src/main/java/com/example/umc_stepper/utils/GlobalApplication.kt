package com.example.umc_stepper.utils

import android.app.Application
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
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

        fun loadCropImage(imageView: ImageView, source: Any) {
            Glide.with(instance)
                .load(source)
                .centerCrop()
                .into(imageView)
        }

        fun loadCropRoundedSquareImage(context: Context, imageView: ImageView, source: Any, rounded : Int) {
            val density = context.resources.displayMetrics.density
            val roundedCorners = RoundedCorners((rounded * density).toInt())

            Glide.with(context)
                .load(source)
                .apply(RequestOptions().transform(CenterCrop(), roundedCorners))
                .into(imageView)
        }
    }
}
