package com.example.umc_stepper.utils.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

// Glide -> GlideApp로 사용
@GlideModule
class GlideModule: AppGlideModule() {

}

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        if (url != null) {
            Glide.with(view.context)
                .load(url)
                .apply(RequestOptions().centerCrop())
                .into(view)
        }
    }
}