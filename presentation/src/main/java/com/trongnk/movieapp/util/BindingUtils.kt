package com.trongnk.movieapp.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("moviePoster")
fun loadMoviePoster(view: ImageView, poster: String?) {
    if (poster == null) return
    val imageResource = view.resources.getIdentifier(poster, "drawable", view.context.packageName)
    val requestOptions = RequestOptions.bitmapTransform(RoundedCorners(10))
    Glide.with(view.context).load(imageResource).apply(requestOptions).into(view)
}

@BindingAdapter("rating")
fun setRating(textView: TextView, rating: Float) {
    textView.text = rating.toString()
}

@BindingAdapter("releasedDate")
fun setReleasedDate(textView: TextView, releasedDate: Date?) {
    releasedDate?.let {
        textView.text = SimpleDateFormat(Constants.RELEASE_DATE_FORMAT, Locale.US).format(it)
    }
}

@BindingAdapter("onClick")
fun View.onClick(clickListener: View.OnClickListener?) = setOnClickListener(clickListener)
