package com.trongnk.movieapp.util.extension

import android.view.LayoutInflater
import android.view.View

val View.inflater: LayoutInflater
    get() = LayoutInflater.from(this.context)