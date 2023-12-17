package com.easit.imageencoder.model

import android.graphics.Bitmap
import android.graphics.Canvas
import java.io.FileOutputStream
import kotlin.random.Random

class EncoderModel1 {
    var width = 0
    var height = 0
    private var partWidth = 0
    private var partHeight = 0
    private var bitmap: Bitmap? = null

    fun splitImage(myBitmap: Bitmap): List<Bitmap> {
        // Read the image
        //val imageFile = File(imagePath)
        //bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)

        bitmap = myBitmap
        // Calculate dimensions and create empty list for parts
        width = bitmap!!.width
        height = bitmap!!.height
        partWidth = width / 4
        partHeight = height / 4
        val parts = mutableListOf<Bitmap>()

        // Loop through rows and columns, extract and add parts to list
        for (y in 0 until 4) {
            for (x in 0 until 4) {
                val subBitmap = Bitmap.createBitmap(partWidth, partHeight, bitmap!!.config)
                val canvas = Canvas(subBitmap)
                canvas.drawBitmap(
                    bitmap!!,
                    x * partWidth.toFloat(),
                    y * partHeight.toFloat(),
                    null
                )
                canvas.save()
                parts.add(subBitmap)
            }
        }
        return parts
    }

    fun shuffleAndJoinImages(splitImages: List<Bitmap>, outputPath: String): Bitmap? {
        // Shuffle the list of split images
        splitImages.shuffled(Random(System.currentTimeMillis()))

        // Create a new bitmap for the rejoined image
        //val rejoinedImage = Bitmap.createBitmap(width, height, bitmap.config)
        val rejoinedImage = bitmap?.let { Bitmap.createBitmap(width, height, it.config) }
        val rejoinedCanvas = rejoinedImage?.let { Canvas(it) }

        // Loop through shuffled images, draw them back together
        for (y in 0 until 4) {
            for (x in 0 until 4) {
                val partImage = splitImages[y * 4 + x]
                rejoinedCanvas?.drawBitmap(
                    partImage,
                    x * partWidth.toFloat(),
                    y * partHeight.toFloat(),
                    null
                )
            }
        }
        rejoinedCanvas?.save()

        // Save the rejoined image
        val fileOutputStream = FileOutputStream(outputPath)
        rejoinedImage?.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        fileOutputStream.close()

        return rejoinedImage
    }
}