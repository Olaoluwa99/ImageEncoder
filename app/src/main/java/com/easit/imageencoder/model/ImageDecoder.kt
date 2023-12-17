package com.easit.imageencoder.model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.media.ExifInterface
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import kotlin.random.Random

class ImageDecoder {
    fun unshuffleImage(shuffledBitmap: Bitmap): Bitmap {
        val imageWidth = shuffledBitmap.width
        val imageHeight = shuffledBitmap.height

        val tileWidth = imageWidth / 4
        val tileHeight = imageHeight / 4
        val unshuffledTiles = mutableListOf<Bitmap>()

        // Create empty tiles with the same dimensions as the individual tiles
        for (y in 0 until 4) {
            for (x in 0 until 4) {
                unshuffledTiles.add(Bitmap.createBitmap(tileWidth, tileHeight, shuffledBitmap.config))
            }
        }

        // Reverse the shuffling logic to map each shuffled part to its original position
        for (y in 0 until 4) {
            for (x in 0 until 4) {
                val partIndex = y * 4 + x
                val shuffledPartX = x * tileWidth
                val shuffledPartY = y * tileHeight

                // Extract the shuffled part from the original shuffled image
                val shuffledPart = Bitmap.createBitmap(
                    shuffledBitmap,
                    shuffledPartX, shuffledPartY,
                    tileWidth, tileHeight
                )

                // Place the shuffled part back into its original position in the unshuffled tiles list
                unshuffledTiles[partIndex] = shuffledPart
            }
        }

        // Reassemble the image from the unshuffled tiles into a new bitmap
        val originalBitmap = Bitmap.createBitmap(imageWidth, imageHeight, shuffledBitmap.config)
        val canvas = Canvas(originalBitmap)
        for (y in 0 until 4) {
            for (x in 0 until 4) {
                val partIndex = y * 4 + x
                val partBitmap = unshuffledTiles[partIndex]
                canvas.drawBitmap(partBitmap, (x * tileWidth).toFloat(), (y * tileHeight).toFloat(), null)
            }
        }

        return originalBitmap
    }

    fun unshuffleAndRotateTiles(shuffledTiles: List<Bitmap>, userSeed: Long): MutableList<Bitmap> {
        val originalTileOrder = shuffledTiles.mapIndexed { index, _ -> index }.toMutableList() // Map shuffled index to original index

        // Un-shuffle the tile order based on their original positions
        val random = Random(userSeed)//Random
        originalTileOrder.shuffle(random)

        val unshuffledTiles = mutableListOf<Bitmap>()
        originalTileOrder.forEachIndexed { index, originalIndex ->
            val shuffledTile = shuffledTiles[originalIndex]
            val unrotatedTile = if (isRotated(shuffledTile)) {
                rotateBitmap(shuffledTile, -90f) // Un-rotate the shuffled tile if needed
            } else {
                shuffledTile
            }
            unshuffledTiles.add(unrotatedTile)
        }

        return unshuffledTiles
    }

    fun splitImageToParts(bitmap: Bitmap): List<Bitmap> {
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

        return tiles
    }

    fun reassembleImage(tiles: List<Bitmap>): Bitmap {
        // Reassemble the image into a new bitmap
        val imageWidth = tiles[0].width * 4
        val imageHeight = tiles[0].height * 4
        val shuffledBitmap = Bitmap.createBitmap(imageWidth, imageHeight, tiles[0].config)
        val canvas = Canvas(shuffledBitmap)

        for (y in 0 until 4) {
            for (x in 0 until 4) {
                val partIndex = y * 4 + x
                val partBitmap = tiles[partIndex]
                canvas.drawBitmap(partBitmap, (x * partBitmap.width).toFloat(), (y * partBitmap.height).toFloat(), null)
            }
        }

        return shuffledBitmap
    }

    private fun rotateBitmap(bitmap: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun bitmapToInputStream(bitmap: Bitmap): InputStream {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream) // Adjust compression format and quality as needed
        return ByteArrayInputStream(byteArrayOutputStream.toByteArray())
    }

    private fun isRotated(bitmap: Bitmap): Boolean {
        // Check if the bitmap has been rotated by analyzing its metadata (if available)
        val exif = ExifInterface(bitmapToInputStream(bitmap))
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        return orientation != ExifInterface.ORIENTATION_NORMAL
    }
}