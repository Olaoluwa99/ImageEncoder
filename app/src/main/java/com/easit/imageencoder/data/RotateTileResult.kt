package com.easit.imageencoder.data

import android.graphics.Bitmap

data class RotateTileResult(
    val shuffledListIndex: MutableList<Int>,
    val tile: MutableList<Bitmap>
)
