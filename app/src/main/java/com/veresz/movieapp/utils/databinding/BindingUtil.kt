package com.veresz.movieapp.utils.databinding

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("visible")
fun View.visible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("visibleOrGone")
fun View.visibleOrGone(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("imageUrl", "placeHolder", "error")
fun ImageView.loadImageFromUrl(url: String?, placeHolder: Drawable, error: Drawable) {
    if (TextUtils.isEmpty(url)) {
        return
    }

    val requestOptions = RequestOptions()
            .placeholder(placeHolder)
            .error(error)

    Glide.with(context)
            .asBitmap()
            .apply(requestOptions)
            .load(url)
            .into(this)
}
