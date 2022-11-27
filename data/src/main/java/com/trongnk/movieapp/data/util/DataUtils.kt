package com.trongnk.movieapp.data.util

fun ClassLoader?.open(file: String) = this?.getResourceAsStream(file)?.reader()
