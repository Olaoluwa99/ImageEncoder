package com.easit.imageencoder.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import java.io.FileOutputStream

class EncoderModel2 {
    fun splitImageAndShuffle(bitmap: Bitmap, outputDir: String): Bitmap{
        //val bitmap = BitmapFactory.decodeFile(imagePath)
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

        // Save each part to file storage
        /*
        for (i in 0 until 16) {
            val filename = "$outputDir/part_$i.png"
            val outputStream = FileOutputStream(filename)
            tiles[i].compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()
        }*/

        // Reassemble the image
        val reassembledBitmap = Bitmap.createBitmap(imageWidth, imageHeight, bitmap.config)
        val canvas = Canvas(reassembledBitmap)
        for (y in 0 until 4) {
            for (x in 0 until 4) {
                val partIndex = y * 4 + x
                val partBitmap = tiles[partIndex]
                canvas.drawBitmap(partBitmap, (x * tileWidth).toFloat(), (y * tileHeight).toFloat(), null)
            }
        }

        // Save the reassembled image (optional)
        // ...
        canvas.save()

        // Save the rejoined image
        val fileOutputStream = FileOutputStream(outputDir)
        reassembledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        fileOutputStream.close()

        return reassembledBitmap

    }
}