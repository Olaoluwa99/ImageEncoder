package com.easit.imageencoder.model

import android.graphics.Bitmap
import android.graphics.Canvas

class EncoderModel3 {
    fun shuffleImage(bitmap: Bitmap): Bitmap {
        val imageWidth = bitmap.width
        val imageHeight = bitmap.height

        val tileWidth = imageWidth / 4
        val tileHeight = imageHeight / 4
        val tiles = mutableListOf<Bitmap>()

        // Split image into 16 parts
        for (y in 0 until 4) {
            for (x in 0 until 4) {
                val croppedBitmap = Bitmap.createBitmap(
                    bitmap,
                    x * tileWidth, y * tileHeight,
                    tileWidth, tileHeight
                )
                tiles.add(croppedBitmap)
            }
        }

        // Shuffle the image parts
        tiles.shuffle()

        // Reassemble the image into a new bitmap
        val shuffledBitmap = Bitmap.createBitmap(imageWidth, imageHeight, bitmap.config)
        val canvas = Canvas(shuffledBitmap)
        for (y in 0 until 4) {
            for (x in 0 until 4) {
                val partIndex = y * 4 + x
                val partBitmap = tiles[partIndex]
                canvas.drawBitmap(partBitmap, (x * tileWidth).toFloat(), (y * tileHeight).toFloat(), null)
            }
        }

        return shuffledBitmap
    }
}