package com.easit.imageencoder

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Shader
import android.graphics.Typeface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.easit.imageencoder.model.ImageEncoder

fun fullImageProcess(
                    text: String,
                    width: Int,
                    height: Int,
                    startColor: Color,
                    endColor: Color,
                    textColor: Color,
                    textSize: Float,
                    typeface: Typeface? = null,
                    shuffleSeed: Long
): Bitmap{
    val response = setBackground(width, height, startColor, endColor)
    val encoder = ImageEncoder()
    val design = encoder.reassembleImage(encoder.shuffleBitmap(encoder.splitImageToParts(4, response), shuffleSeed))
    return setTextOnBackground(design, text, width, height, textColor, textSize, typeface)
}

fun setTextOnBackground(
    background: Bitmap,
    text: String,
    width: Int,
    height: Int,
    textColor: Color,
    textSize: Float,
    typeface: Typeface? = null
) : Bitmap {
    val bitmap = Bitmap.createBitmap(background, 0, 0, width, height)
    val canvas = Canvas(bitmap)
    val paint = Paint().apply {
        this.color = textColor.toArgb()
        this.textSize = textSize
        this.typeface = typeface ?: Typeface.DEFAULT
    }

    val bounds = Rect()
    paint.getTextBounds(text, 0, text.length, bounds)

    val x = (width - bounds.width()) / 2
    val y = (height + bounds.height()) / 2

    canvas.drawText(text, x.toFloat(), y.toFloat(), paint)

    return bitmap
}

fun setBackground(width: Int, height: Int, startColor: Color, endColor: Color) : Bitmap{
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    val gradient = LinearGradient(
        0f, 0f, width.toFloat(), height.toFloat(),
        intArrayOf(startColor.toArgb(), endColor.toArgb()), null, Shader.TileMode.CLAMP
    )

    val paint = Paint().apply {
        this.shader = gradient
    }

    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    return bitmap
}

fun getProfileById(profileId: String): Bitmap{
    val initials = "${profileId[0]}${profileId[1]}"
    val startColorArgb = ("${profileId[2]}${profileId[3]}${profileId[4]}${profileId[5]}${profileId[6]}" +
            "${profileId[7]}${profileId[8]}${profileId[9]}${profileId[10]}${profileId[11]}").toInt()
    val endColorArgb = ("${profileId[12]}${profileId[13]}${profileId[14]}${profileId[15]}${profileId[16]}" +
            "${profileId[17]}${profileId[18]}${profileId[19]}${profileId[20]}${profileId[21]}").toInt()
    val backgroundShuffleSeed = ("${profileId[22]}${profileId[23]}${profileId[24]}" +
            "${profileId[25]}${profileId[26]}${profileId[27]}").toLong()
    val startColor = Color(startColorArgb)
    val endColor = Color(endColorArgb)

    //val k = Color.valueOf(startColorArgb)
    return fullImageProcess(
        text = initials, width = 200, height = 200, startColor = startColor, endColor = endColor,
        textColor = Color.White, textSize = 100f, typeface = Typeface.MONOSPACE, shuffleSeed = backgroundShuffleSeed
    )
}