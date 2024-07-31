package com.example.umc_stepper.utils.extensions

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

class ColorExtension {
    companion object {
        @JvmStatic
        @BindingAdapter("app:backgroundResource")
        fun setBackgroundResource(view: TextView, resource: Int) {
            view.setBackgroundResource(resource)
        }

        @JvmStatic
        @BindingAdapter("app:textColorResource")
        fun setTextColorResource(view: TextView, resource: Int) {
            view.setTextColor(ContextCompat.getColor(view.context, resource))
        }
    }
}